package kuit.subway.service;

import kuit.subway.acceptance.fixtures.LineFixture;
import kuit.subway.acceptance.fixtures.StationFixture;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.request.section.DeleteSectionRequest;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.response.station.ShowStationResponse;
import kuit.subway.utils.exception.LineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kuit.subway.utils.BaseResponseStatus.CANNOT_DELETE_SECTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Transactional을 통해 변경 사항 롤백
@SpringBootTest
@Transactional
public class LineServiceTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private LineService lineService;

    private Line 이호선;
    private Station 성수역;
    private Station 강남역;
    private Station 건대역;
    @BeforeEach
    void setUp(){
        성수역 = new Station(StationFixture.성수역);
        강남역 = new Station(StationFixture.강남역);
        건대역 = new Station(StationFixture.건대역);
        stationRepository.save(성수역);
        stationRepository.save(강남역);
        stationRepository.save(건대역);

        이호선 = Line.builder()
                .name(LineFixture.이호선이름)
                .color(LineFixture.GREEN)
                .build();

        lineRepository.save(이호선);

        Section 이호선첫구간 = Section.builder()
                .downStation(강남역)
                .upStation(성수역)
                .distance(10L)
                .build();

        이호선첫구간.addLine(이호선);
        sectionRepository.save(이호선첫구간);
    }
    @Test
    @DisplayName("구간 추가")
    @Transactional
    void addSectionToLine() {
        // given
        SectionRequest request = new SectionRequest(3L, 건대역.getId(), 성수역.getId());
        // when
        ShowLineResponse response = lineService.addSectionToLine(이호선.getId(), request);

        // then
        assertThat(response.getStations())
                .extracting("name")
                .containsExactly("성수역", "건대역", "강남역");
    }

    @Nested
    @DisplayName("구간 삭제 테스트")
    class DeleteSection{
        @Nested
        @DisplayName("성공")
        class Success{
            @BeforeEach
            void setUp(){
                SectionRequest request = new SectionRequest(3L, 건대역.getId(), 성수역.getId());
                lineService.addSectionToLine(이호선.getId(), request);
            }

            private List<String> getStationsName() {
                return lineService.getLine(이호선.getId()).getStations().stream()
                        .map(ShowStationResponse::getName)
                        .toList();
            }

            @Test
            @DisplayName("하행종점역 삭제")
            @Transactional
            void deleteSection_WHEN_하행종점역() {
                // given
                DeleteSectionRequest request = new DeleteSectionRequest(강남역.getId());

                // when
                lineService.deleteSection(이호선.getId(), request);

                // then
                List<String> stationsName = getStationsName();

                assertEquals(2, stationsName.size());
                assertEquals(성수역.getName(), stationsName.get(0));
                assertEquals(건대역.getName(), stationsName.get(1));
            }


            }
        }

        @Nested
        @DisplayName("실패")
        class Fail{
            @Test
            @DisplayName("싱글 구간 삭제 불가")
            @Transactional
            void deleteSection_WHEN_싱글구간() {
                // given
                DeleteSectionRequest request = new DeleteSectionRequest(강남역.getId());

                // when, then
                assertThatThrownBy(()->lineService.deleteSection(이호선.getId(), request))
                        .isInstanceOf(LineException.class)
                        .extracting("status")
                        .isEqualTo(CANNOT_DELETE_SECTION);
            }
        }
    }
}
