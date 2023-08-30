package kuit.subway.path.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.domain.Section;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.path.dto.response.PathResponse;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static kuit.subway.global.exception.CustomExceptionStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PathServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private LineRepository lineRepository;

    @InjectMocks
    private PathService pathService;

    private Station upStation;
    private Station downStation;
    private Line line;

    @BeforeEach
    void init() {
        upStation = Station.builder().id(1L).name("둔촌역").build();
        downStation = Station.builder().id(2L).name("군자역").build();
        line = Line.builder().id(1L).name("5호선").color("purple").build();
        line.addSection(Section.createSection(10L, line, upStation, downStation));
    }

    @DisplayName("경로를 조회한다.")
    @Test
    void findPath() {
        //given
        given(stationRepository.findById(1L))
                .willReturn(Optional.ofNullable(upStation));
        given(stationRepository.findById(2L))
                .willReturn(Optional.ofNullable(downStation));
        given(lineRepository.findAll())
                .willReturn(List.of(line));

        //when
        PathResponse response = pathService.findPath(1L, 2L);

        //then
        verify(stationRepository, times(2)).findById(anyLong());
        verify(lineRepository, times(1)).findAll();
        assertAll(
                () -> assertThat(response.getStations()).hasSize(2)
                        .extracting("name")
                        .containsExactly("둔촌역", "군자역"),
                () -> assertThat(response.getDistance()).isEqualTo(10)
        );
    }

    @DisplayName("출발역과 도착역이 같은 경우 예외가 발생한다.")
    @Test
    void findPath_Throw_Exception_If_Duplicated_Source_And_Target() {
        //when & then
        assertThatThrownBy(() -> pathService.findPath(1L, 1L))
                .isInstanceOf(SubwayException.class)
                .extracting("status")
                .isEqualTo(DUPLICATED_PATH_REQUEST);
    }

    @DisplayName("출발역과 도착역이 연결되지 않는 경우 예외가 발생한다.")
    @Test
    void findPath_Throw_Exception_If_Not_Connect_Path() {
        //given
        Station notConnectedUpStation = Station.builder().id(3L).name("안연결상행역").build();
        Station notConnectedDownStation = Station.builder().id(4L).name("안연결하행역").build();
        Line notConnectedLine = Line.builder().id(2L).name("7호선").color("green").build();
        notConnectedLine.addSection(Section.createSection(10L, notConnectedLine, notConnectedUpStation, notConnectedDownStation));

        given(stationRepository.findById(1L))
                .willReturn(Optional.ofNullable(upStation));
        given(stationRepository.findById(3L))
                .willReturn(Optional.ofNullable(notConnectedUpStation));
        given(lineRepository.findAll())
                .willReturn(List.of(line, notConnectedLine));

        //when & then
        assertThatThrownBy(() -> pathService.findPath(1L, 3L))
                .isInstanceOf(SubwayException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_PATH);
    }

    @DisplayName("존재하지 않은 출발역을 조회할 경우 예외가 발생한다.")
    @Test
    void findPath_Throw_Exception_If_Not_Existed_Source(){
        //when & then
        assertThatThrownBy(() -> pathService.findPath(3L, 2L))
                .isInstanceOf(SubwayException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_STATION);
    }

    @DisplayName("존재하지 않은 도착역을 조회할 경우 예외가 발생한다.")
    @Test
    void findPath_Throw_Exception_If_Not_Existed_Target(){
        //when & then
        assertThatThrownBy(() -> pathService.findPath(1L, 3L))
                .isInstanceOf(SubwayException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_STATION);
    }
}