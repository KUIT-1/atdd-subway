package kuit.subway.service;

import kuit.subway.acceptance.fixtures.LineFixture;
import kuit.subway.acceptance.fixtures.StationFixture;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.ShowLineResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
}
