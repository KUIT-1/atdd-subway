package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.SectionRepository;
import kuit.subway.utils.exception.LineException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static kuit.subway.utils.BaseResponseStatus.NONE_LINE;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService {
    private final SectionRepository sectionRepository;

    public void createSection(Line line, Station downStation, Station upStation, Long distance) {
        if(line == null) throw new LineException(NONE_LINE);
        Section section = Section.builder()
                        .downStation(downStation)
                        .upStation(upStation)
                        .distance(distance)
                        .build();

        section.addLine(line);
        sectionRepository.save(section);
    }

}
