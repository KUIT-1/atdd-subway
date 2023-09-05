package kuit.subway.line.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.domain.Section;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static kuit.subway.global.exception.CustomExceptionStatus.*;
import static kuit.subway.utils.fixtures.LineFixtures.노선_변경_요청;
import static kuit.subway.utils.fixtures.LineFixtures.노선_요청;
import static kuit.subway.utils.fixtures.SectionFixtures.구간_생성_요청;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private LineRepository lineRepository;

    @InjectMocks
    private LineService lineService;

    private Station upStation;
    private Station downStation;
    private Line line;

    @BeforeEach
    void init() {
        upStation = Station.builder().id(1L).name("강남역").build();
        downStation = Station.builder().id(2L).name("성수역").build();
        line = Line.builder().id(1L).name("경춘선").color("green").build();
    }

    @DisplayName("라인을 생성한다.")
    @Test
    void createLine(){
        //given
        given(lineRepository.save(any(Line.class)))
                .willReturn(line);
        given(stationRepository.findById(1L))
                .willReturn(Optional.ofNullable(upStation));
        given(stationRepository.findById(2L))
                .willReturn(Optional.ofNullable(downStation));

        //when
        LineCreateResponse result =
                lineService.createLine(노선_요청("경춘선", "green", 10L, 1L, 2L));

        //then
        verify(stationRepository, times(2)).findById(anyLong());
        verify(lineRepository, times(1)).save(any(Line.class));
        assertThat(result.getId()).isEqualTo(line.getId());
    }

    @DisplayName("라인을 조회한다.")
    @Test
    void showLine(){
        //given
        line.addSection(Section.createSection(10L, line , upStation, downStation));

        given(lineRepository.findById(1L))
                .willReturn(Optional.ofNullable(line));

        //when
        LineResponse response = lineService.showLine(1L);

        //then
        verify(lineRepository, times(1)).findById(anyLong());
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getName()).isEqualTo(line.getName()),
                () -> assertThat(response.getColor()).isEqualTo(line.getColor())
        );
    }

    @DisplayName("라인을 변경한다.")
    @Test
    void updateLine() {
        //given
        line.addSection(Section.createSection(10L, line , upStation, downStation));

        given(lineRepository.findById(1L))
                .willReturn(Optional.ofNullable(line));

        //when
        LineResponse response = lineService.updateLine(1L, 노선_변경_요청("신분당선", "yellow"));

        //then
        verify(lineRepository, times(1)).findById(anyLong());
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(line.getName()).isEqualTo("신분당선"),
                () -> assertThat(line.getColor()).isEqualTo("yellow")
        );
    }

    @DisplayName("Section Test")
    @Nested
    class SectionTest {

        private Station newUpStation;
        private Station newDownStation;

        @BeforeEach
        void init() {
            line.addSection(Section.createSection(10L, line, upStation, downStation));
            newUpStation = downStation;
            newDownStation = Station.builder().id(3L).name("잠실역").build();
            line.addSection(Section.createSection(9L, line, newUpStation, newDownStation));
        }

        @DisplayName("기존의 구간 사이에 새로운 구간을 추가한다.")
        @Test
        void createSection() {
            //given
            newDownStation = Station.builder().id(4L).name("잠실새내역").build();

            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(2L))
                    .willReturn(Optional.ofNullable(newUpStation));
            given(stationRepository.findById(4L))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when
            LineResponse response = lineService.createSection(1L, 구간_생성_요청(5L, 2L, 4L));

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(2)).findById(anyLong());
            assertThat(response.getStations()).hasSize(4)
                    .extracting("name")
                    .containsExactly("강남역", "성수역", "잠실새내역" ,"잠실역");
        }

        @DisplayName("상행 종점으로 새로운 구간을 추가한다.")
        @Test
        void createSection_At_Up_Station() {
            //given
            newDownStation = upStation;
            newUpStation = Station.builder().id(4L).name("잠실새내역").build();

            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(4L))
                    .willReturn(Optional.ofNullable(newUpStation));
            given(stationRepository.findById(1L))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when
            LineResponse response = lineService.createSection(1L, 구간_생성_요청(5L, 4L, 1L));

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(2)).findById(anyLong());
            assertThat(response.getStations()).hasSize(4)
                    .extracting("name")
                    .containsExactly("잠실새내역", "강남역", "성수역", "잠실역");
        }

        @DisplayName("하행 종점으로 새로운 구간을 추가한다.")
        @Test
        void createSection_At_Down_Station() {
            //given
            newUpStation = newDownStation;
            newDownStation = Station.builder().id(4L).name("잠실새내역").build();

            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(3L))
                    .willReturn(Optional.ofNullable(newUpStation));
            given(stationRepository.findById(4L))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when
            LineResponse response = lineService.createSection(1L, 구간_생성_요청(5L, 3L, 4L));

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(2)).findById(anyLong());
            assertThat(response.getStations()).hasSize(4)
                    .extracting("name")
                    .containsExactly("강남역", "성수역", "잠실역", "잠실새내역");
        }

        @DisplayName("상행역과 하행역이 모두 노선에 등록된 경우, 예외가 발생한다.")
        @Test
        void createSection_Throw_Exception_If_Existed_Station() {
            //given
            newDownStation = downStation;

            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(3L))
                    .willReturn(Optional.ofNullable(newUpStation));
            given(stationRepository.findById(2L))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when & then
            assertThatThrownBy(() -> lineService.createSection(1L, 구간_생성_요청(5L, 3L, 2L)))
                    .isInstanceOf(SubwayException.class)
                    .extracting("status")
                    .isEqualTo(EXISTED_STATION_IN_SECTIONS);
        }

        @DisplayName("역 사이에 새로운 역을 등록하는 경우, 기존 역 사이 길이보다 크거나 같으면 예외가 발생한다.")
        @Test
        void createSection_Throw_Exception_If_Exceed_Distance() {
            //given
            newDownStation = Station.builder().id(4L).name("잠실새내역").build();

            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(2L))
                    .willReturn(Optional.ofNullable(newUpStation));
            given(stationRepository.findById(4L))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when
            assertThatThrownBy(() -> lineService.createSection(1L, 구간_생성_요청(10L, 2L, 4L)))
                    .isInstanceOf(SubwayException.class)
                    .extracting("status")
                    .isEqualTo(EXCEED_DISTANCE);
        }

        @DisplayName("구간 삭제중, 중간역이 제거될 경우 재배치한다.")
        @Test
        void removeSection(){
            //given
            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(downStation));

            //when
            LineResponse response = lineService.deleteSection(1L, 2L);

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(1)).findById(anyLong());
            assertThat(response.getStations()).hasSize(2)
                            .extracting("name")
                            .containsExactly("강남역","잠실역");
        }

        @DisplayName("구간 삭제중, 상행 종점이 제거될 경우 다음 역이 종점이 된다.")
        @Test
        void removeSection_If_Remove_First_Up_Station(){
            //given
            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(upStation));

            //when
            LineResponse response = lineService.deleteSection(1L, 1L);

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(1)).findById(anyLong());
            assertThat(response.getStations()).hasSize(2)
                    .extracting("name")
                    .containsExactly("성수역","잠실역");
        }

        @DisplayName("구간 삭제중, 하행 종점이 제거될 경우 그 앞의 역이 종점이 된다.")
        @Test
        void removeSection_If_Remove_Last_Down_Station(){
            //given
            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when
            LineResponse response = lineService.deleteSection(1L, 3L);

            //then
            verify(lineRepository, times(1)).findById(anyLong());
            verify(stationRepository, times(1)).findById(anyLong());
            assertThat(response.getStations()).hasSize(2)
                    .extracting("name")
                    .containsExactly("강남역","성수역");
        }

        @DisplayName("노선에 등록되어 있지 않은 역은 제거가 불가능하다.")
        @Test
        void removeSection_Throw_Exception_If_Not_Existed_Station() {
            newDownStation = Station.builder().id(4L).name("잠실새내역").build();

            //given
            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(newDownStation));

            //when & then
            assertThatThrownBy(() -> lineService.deleteSection(1L, 4L))
                    .isInstanceOf(SubwayException.class)
                    .extracting("status")
                    .isEqualTo(NOT_EXISTED_STATION_IN_SECTION);
        }

        @DisplayName("노선에 등록되어 있지 않은 역은 제거가 불가능하다.")
        @Test
        void removeSection_Throw_Exception_If_Only_One_Section() {
            //given
            given(lineRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(line));
            given(stationRepository.findById(anyLong()))
                    .willReturn(Optional.ofNullable(newDownStation));

            lineService.deleteSection(1L, 3L);

            //when & then
            assertThatThrownBy(() -> lineService.deleteSection(1L, 2L))
                    .isInstanceOf(SubwayException.class)
                    .extracting("status")
                    .isEqualTo(UNDER_MINIMUM_SECTION_SIZE);
        }
    }
}