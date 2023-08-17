package kuit.subway.line.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.dto.request.LineCreateRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.station.domain.Station;
import kuit.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_STATION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public LineCreateResponse createLine(LineCreateRequest request) {
        Station upStation = stationRepository.findById(request.getUpStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Station downStation = stationRepository.findById(request.getDownStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Line line = request.toEntity();
        line.addStations(upStation, downStation);

        lineRepository.save(line);
        return LineCreateResponse.of(line);
    }
}
