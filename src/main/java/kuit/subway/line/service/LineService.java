package kuit.subway.line.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.domain.Section;
import kuit.subway.line.dto.request.LineRequest;
import kuit.subway.line.dto.request.LineUpdateRequest;
import kuit.subway.line.dto.request.SectionRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuit.subway.global.exception.CustomExceptionStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public LineCreateResponse createLine(LineRequest request) {
        validateDuplicatedStations(request);
        Line line = lineRepository.save(request.toEntity());

        Station upStation = stationRepository.findById(request.getUpStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));
        Station downStation = stationRepository.findById(request.getDownStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Section section = Section.createSection(request.getDistance(), line, upStation, downStation);
        line.addSection(section);

        return LineCreateResponse.of(line);
    }

    public LineResponse showLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        return LineResponse.of(line);
    }

    @Transactional
    public LineResponse updateLine(Long lineId, LineUpdateRequest request) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        line.update(request.getName(), request.getColor());

        return LineResponse.of(line);
    }

    @Transactional
    public void deleteLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        lineRepository.delete(line);
    }

    @Transactional
    public LineResponse createSection(Long lineId, SectionRequest request) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        Station upStation = stationRepository.findById(request.getUpStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));
        Station downStation = stationRepository.findById(request.getDownStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Section section = Section.createSection(request.getDistance(), line, upStation, downStation);
        line.addSection(section);

        return LineResponse.of(line);
    }

    @Transactional
    public LineResponse deleteSection(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        line.removeSection();

        return LineResponse.of(line);
    }

    private void validateDuplicatedStations(LineRequest request) {
        if (request.getUpStationId().equals(request.getDownStationId())) {
            throw new SubwayException(DUPLICATED_UP_STATION_AND_DOWN_STATION);
        }
    }
}
