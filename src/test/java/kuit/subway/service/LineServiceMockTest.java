package kuit.subway.service;

import kuit.subway.acceptance.fixtures.LineFixture;
import kuit.subway.acceptance.fixtures.SectionStep;
import kuit.subway.acceptance.fixtures.StationFixture;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.request.line.CreateLineRequest;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.utils.exception.LineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static kuit.subway.utils.BaseResponseStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {
    @Mock
    StationRepository stationRepository;

    @Mock
    SectionRepository sectionRepository;

    @Mock
    LineRepository lineRepository;

    @InjectMocks
    LineService lineService;

    @Nested
    @DisplayName("노선 - 구간 도입 테스트")
    class SectionTest {
        private Station 성수역;
        private Station 강남역;
        private Line 이호선;
        private Section 이호선첫구간;

        @BeforeEach
        void setUp() {
            성수역 = StationFixture.create_역(StationFixture.성수역INFO);
            강남역 = StationFixture.create_역(StationFixture.강남역INFO);
            이호선 = LineFixture.create_이호선();

            이호선첫구간 = SectionStep.create_구간(강남역, 성수역, LineFixture.TEN);
        }

        @Nested
        @DisplayName("구간 있는 노선 생성")
        class createLineTest {

            @Test
            @DisplayName("성공")
            void createLineTest_success() {
                //given
                when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                when(stationRepository.findById(2L)).thenReturn(Optional.of(강남역));

                given(lineRepository.save(any(Line.class))).willReturn(이호선);
                given(sectionRepository.save(any(Section.class))).willReturn(이호선첫구간);

                // when
                CreateLineRequest request = new CreateLineRequest(LineFixture.GREEN, 10L, LineFixture.이호선이름, 2L, 1L);
                ShowLineResponse response = lineService.createLine(request);

                assertEquals(response.getName(), 이호선.getName());
                assertEquals(response.getStations().size(), 2);
            }

            @Test
            @DisplayName("실패 - 중복된 노선 이름")
            void createLineTest_fail_duplicatedName() {
                //given
                when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                when(stationRepository.findById(2L)).thenReturn(Optional.of(강남역));

                when(lineRepository.existsLineByName(any(String.class))).thenReturn(true);

                // when
                CreateLineRequest request = new CreateLineRequest(LineFixture.GREEN, 10L, LineFixture.이호선이름, 2L, 1L);

                assertThatThrownBy(()->lineService.createLine(request))
                        .isInstanceOf(LineException.class)
                        .extracting("status")
                        .isEqualTo(DUPLICATED_LINENAME);

            }
        }

        @Nested
        @DisplayName("구간 있는 노선에 구간 추가")
        class addSectionTest {
            private Station 교대역;
            private Station 뚝섬역;
            private Station 건대역;

            @BeforeEach
            void setUp() {
                이호선.addSection(이호선첫구간);
                교대역 = StationFixture.create_역(StationFixture.교대역INFO);
                뚝섬역 = StationFixture.create_역(StationFixture.뚝섬역INFO);
                건대역 = StationFixture.create_역(StationFixture.건대역INFO);
            }


            @Nested
            @DisplayName("성공 케이스")
            class success {
                @Test
                @DisplayName("하행종점역의 하행에 구간 추가")
                void addSectionFromLastDownStation() {
                /*  given
                    2호선 : 성수역 - 강남역
                    추가할 구간 : 강남역 - 교대역(id:3)
                */
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(2L)).thenReturn(Optional.of(강남역));
                    when(stationRepository.findById(3L)).thenReturn(Optional.of(교대역));

                    SectionRequest request = new SectionRequest(10L, 3L, 2L);

                    // when
                    ShowLineResponse response = lineService.addSectionToLine(1L, request);
                    // then
                    assertThat(response.getStations())
                            .extracting("name")
                            .containsExactly("성수역", "강남역", "교대역");
                }

                @Test
                @DisplayName("상행종점역의 상행에 구간 추가")
                void addSectionFromLastUpStation() {
                /*  given
                    2호선 : 성수역 - 강남역
                    추가할 구간 : 뚝섬역 - 성수역
                */
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                    when(stationRepository.findById(4L)).thenReturn(Optional.of(뚝섬역));

                    SectionRequest request = new SectionRequest(10L, 1L, 4L);

                    // when
                    ShowLineResponse response = lineService.addSectionToLine(1L, request);
                    // then
                    assertThat(response.getStations())
                            .extracting("name")
                            .containsExactly("뚝섬역", "성수역", "강남역");
                }

                @Test
                @DisplayName("노선 중간에 구간 추가 - 상행역이 존재")
                void addSectionAtMiddleUp() {
                /*  given
                    2호선 : 성수역 - 강남역
                    추가할 구간 : 성수역 - 건대역
                */
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                    when(stationRepository.findById(5L)).thenReturn(Optional.of(건대역));

                    SectionRequest request = new SectionRequest(9L, 5L, 1L);

                    // when
                    ShowLineResponse response = lineService.addSectionToLine(1L, request);
                    // then
                    assertThat(response.getStations())
                            .extracting("name")
                            .containsExactly("성수역", "건대역", "강남역");
                }

                @Test
                @DisplayName("노선 중간에 구간 추가 - 하행역이 존재")
                void addSectionAtMiddleDown() {
                /*  given
                    2호선 : 성수역 - 강남역
                    추가할 구간 : 건대역 - 강남역
                */
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(2L)).thenReturn(Optional.of(강남역));
                    when(stationRepository.findById(5L)).thenReturn(Optional.of(건대역));

                    SectionRequest request = new SectionRequest(9L, 2L, 5L);

                    // when
                    ShowLineResponse response = lineService.addSectionToLine(1L, request);
                    // then
                    assertThat(response.getStations())
                            .extracting("name")
                            .containsExactly("성수역", "건대역", "강남역");
                }
            }

            @Nested
            @DisplayName("예외 케이스")
            class fail{
                @Test
                @DisplayName("상행역과 하행역 둘 다 포함되어있지 않을 때 추가 불가")
                void absenceOfBothStations(){
                    // given
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(3L)).thenReturn(Optional.of(교대역));
                    when(stationRepository.findById(5L)).thenReturn(Optional.of(건대역));

                    SectionRequest request = new SectionRequest(10L, 5L, 3L);

                    // when
                    // then
                    assertThatThrownBy(()->lineService.addSectionToLine(1L, request))
                            .isInstanceOf(LineException.class)
                            .extracting("status")
                            .isEqualTo(NEITHER_STATIONS_NOT_REGISTERED);
                }

                @Test
                @DisplayName("상행역과 하행역 둘 다 등록되어있을 때 추가 불가")
                void already_Registered_Section(){
                    // given
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(2L)).thenReturn(Optional.of(강남역));
                    when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));

                    SectionRequest request = new SectionRequest(10L, 2L, 1L);

                    // when
                    // then
                    assertThatThrownBy(()->lineService.addSectionToLine(1L, request))
                            .isInstanceOf(LineException.class)
                            .extracting("status")
                            .isEqualTo(ALREADY_REGISTERED_SECTION);
                }

                @Test
                @DisplayName("기존 역 사이 길이보다 크거나 같으면 추가 불가")
                void invalid_distance(){
                    // given
                    when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                    when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                    when(stationRepository.findById(5L)).thenReturn(Optional.of(건대역));

                    SectionRequest request = new SectionRequest(10L, 5L, 1L);

                    // when
                    // then
                    assertThatThrownBy(()->lineService.addSectionToLine(1L, request))
                            .isInstanceOf(LineException.class)
                            .extracting("status")
                            .isEqualTo(INVALID_DISTANCE);

                }

            }
        }

    }
}
