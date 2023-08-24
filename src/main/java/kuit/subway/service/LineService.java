package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.request.line.CreateLineRequest;
import kuit.subway.request.line.UpdateLineRequest;
import kuit.subway.request.section.DeleteSectionRequest;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.CreateLineResponse;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.utils.exception.LineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kuit.subway.utils.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LineService {
    private final LineRepository lineRepository;
    private final SectionRepository sectionRepository;
    private final StationService stationService;
    private final SectionService sectionService;
    @Transactional
    public CreateLineResponse createLine(CreateLineRequest request) {
        Station downStation = stationService.findById(request.getDownStationId());
        Station upStation = stationService.findById(request.getUpStationId());
        checkDuplicateName(request.getName(), null);

        Line line = Line.builder()
                .name(request.getName())
                .color(request.getColor())
                .build();

        lineRepository.save(line);
        sectionService.createSection(line, downStation, upStation, request.getDistance());

        return CreateLineResponse.from(line);
    }

    @Transactional(readOnly = true)
    public ShowLineResponse getLine(Long id) {
        Line line = findById(id);
        return ShowLineResponse.from(line);
    }

    @Transactional(readOnly = true)
    public List<ShowLineResponse> getLines() {
        List<Line> lines = lineRepository.findAll();

        if(lines.isEmpty()) throw new LineException(EMPTY_INFO);

        return lines.stream().map(ShowLineResponse::from).toList();
    }

    @Transactional
    public ShowLineResponse updateLine(Long id, UpdateLineRequest request) {
        Line line = findById(id);
        checkDuplicateName(request.getName(), id);
        checkDuplicateColor(request.getColor(), id);
        line.update(request);

        return ShowLineResponse.from(line);
    }

    @Transactional
    public void deleteLine(Long id) {
        if(!lineRepository.existsLineById(id))
            throw new LineException(NONE_LINE);
        lineRepository.deleteById(id);
    }

    // requestBody를 바탕으로 Section을 생성한 후 Line에 추가한다.
    @Transactional
    public ShowLineResponse addSectionToLine(Long line_id, SectionRequest request) {
        Line line = findById(line_id);
        Station downStation = stationService.findById(request.getDownStationId());
        Station upStation = stationService.findById(request.getUpStationId());

        line.isSectionRegistrable(request.getUpStationId(), request.getDownStationId());

        sectionService.createSection(line, downStation, upStation, request.getDistance());

        return ShowLineResponse.from(line);
    }

    @Transactional
    public void deleteSection(Long line_id, DeleteSectionRequest request) {
        Line line = findById(line_id);

        line.isSectionDeletable(request.getDownStationId());

        Section section = sectionRepository.findByLineIdAndStationsId(line_id, request.getUpStationId(), request.getDownStationId());
        line.deleteSection(section);
        sectionRepository.deleteById(section.getId());
    }

    private Line findById(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(() -> new LineException(NONE_LINE));
    }

    private void checkDuplicateName(String name, Long line_id) {
        if(!lineRepository.existsLineByName(name)) return;
        if(line_id == null)
            throw new LineException(DUPLICATED_LINENAME);
        if(!lineRepository.findLineByName(name).getId().equals(line_id))
            throw new LineException(DUPLICATED_LINENAME);
    }

    private void checkDuplicateColor(String color, Long line_id) {
        if(!lineRepository.existsLineByColor(color)) return;
        if(line_id == null)
            throw new LineException(DUPLICATED_LINECOLOR);
        if(!lineRepository.findLineByColor(color).getId().equals(line_id))
            throw new LineException(DUPLICATED_LINECOLOR);
    }

}
