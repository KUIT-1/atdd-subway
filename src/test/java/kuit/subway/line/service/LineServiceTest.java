package kuit.subway.line.service;

import kuit.subway.line.domain.Line;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static kuit.subway.utils.fixtures.LineFixtures.노선_요청;
import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("라인을 생성한다.")
    @Test
    void createLine(){
        //given
        Station 강남역 = Station.builder().id(1L).name("강남역").build();
        Station 성수역 = Station.builder().id(2L).name("성수역").build();
        Line 경춘선 = Line.builder().id(1L).name("경춘선").color("green").build();

        given(lineRepository.save(any(Line.class)))
                .willReturn(경춘선);
        given(stationRepository.findById(1L))
                .willReturn(Optional.ofNullable(강남역));
        given(stationRepository.findById(2L))
                .willReturn(Optional.ofNullable(성수역));

        //when
        LineCreateResponse result =
                lineService.createLine(노선_요청("경춘선", "green", 10L, 2L, 1L));

        //then
        verify(stationRepository, times(2)).findById(anyLong());
        verify(lineRepository, times((1))).save(any(Line.class));
        assertThat(result.getId()).isEqualTo(경춘선.getId());
    }

    @DisplayName("라인을 조회한다.")
    @Test
    void showLine(){
        //given
        Line line = Line.builder().id(1L).name("경춘선").color("green").build();

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
}