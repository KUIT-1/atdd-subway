package kuit.subway.service;

import kuit.subway.acceptance.fixtures.LineFixture;
import kuit.subway.acceptance.fixtures.SectionFixture;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.request.line.CreateLineRequest;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.acceptance.fixtures.StationFixture;
import kuit.subway.utils.exception.LineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static kuit.subway.utils.BaseResponseStatus.DUPLICATED_LINENAME;
import static org.assertj.core.api.Assertions.*;
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
            성수역 = StationFixture.create_성수역(); // 1L
            강남역 = StationFixture.create_강남역(); // 2L

            이호선 = LineFixture.create_이호선(); // 1L

            이호선첫구간 = SectionFixture.create_구간(강남역, 성수역);
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
        class addLineTest{
            private Station 교대역;
            private Station 뚝섬역;
            private Station 건대역;

            @BeforeEach
            void setUp() {
                이호선.addSection(이호선첫구간);
                교대역 = StationFixture.create_교대역(); // 3L
                뚝섬역 = StationFixture.create_뚝섬역(); // 4L
                건대역 = StationFixture.create_건대역(); // 5L
            }

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

                Section 강남_교대_구간 = SectionFixture.create_구간(강남역, 교대역);

                given(sectionRepository.save(any(Section.class))).willReturn(강남_교대_구간);

                SectionRequest request = new SectionRequest(10L, 3L, 2L);
//
                // when
                ShowLineResponse response = lineService.addSectionToLine(1L, request);
                // then
                assertEquals("2호선", response.getName());

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

                Section 강남_교대_구간 = SectionFixture.create_구간(강남역, 교대역);

                given(sectionRepository.save(any(Section.class))).willReturn(강남_교대_구간);

                SectionRequest request = new SectionRequest(10L, 1L, 4L);

                // when
                ShowLineResponse response = lineService.addSectionToLine(1L, request);
                // then
                assertEquals("2호선", response.getName());

                assertThat(response.getStations())
                        .extracting("name")
                        .containsExactly("뚝섬역", "성수역", "강남역");
            }

            @Test
            @DisplayName("노선 중간에 구간 추가")
            void addSectionAtMiddle() {
                /*  given
                    2호선 : 성수역 - 강남역
                    추가할 구간 : 성수역 - 건대역
                */
                when(lineRepository.findById(1L)).thenReturn(Optional.of(이호선));
                when(stationRepository.findById(1L)).thenReturn(Optional.of(성수역));
                when(stationRepository.findById(5L)).thenReturn(Optional.of(건대역));

                Section 성수_건대_구간 = SectionFixture.create_구간(성수역, 건대역);

                given(sectionRepository.save(any(Section.class))).willReturn(성수_건대_구간);

                SectionRequest request = new SectionRequest(10L, 5L, 1L);

                // when
                ShowLineResponse response = lineService.addSectionToLine(1L, request);
                // then
                assertEquals("2호선", response.getName());

                assertThat(response.getStations())
                        .extracting("name")
                        .containsExactly("성수역", "건대역", "강남역");
            }
        }

    }
}
