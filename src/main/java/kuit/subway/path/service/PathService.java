package kuit.subway.path.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.domain.Sections;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.path.domain.SubwayMap;
import kuit.subway.path.dto.response.PathResponse;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kuit.subway.global.exception.CustomExceptionStatus.DUPLICATED_PATH_REQUEST;
import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_STATION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PathService {

    private final StationRepository stationRepository;
    private final LineRepository lineRepository;

    public PathResponse findPath(Long sourceStationId, Long targetStationId) {
        validatePath(sourceStationId, targetStationId);
        Station sourceStation = stationRepository.findById(sourceStationId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Station targetStation = stationRepository.findById(targetStationId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        List<Line> lines = lineRepository.findAll();
        SubwayMap subwayMap = SubwayMap.builder().lines(lines).build();

        Sections sections = subwayMap.findPath(sourceStation, targetStation);
        return PathResponse.of(sections);
    }

    private void validatePath(Long sourceStationId, Long targetStationId) {
        if (sourceStationId.equals(targetStationId)) {
            throw new SubwayException(DUPLICATED_PATH_REQUEST);
        }
    }
}
