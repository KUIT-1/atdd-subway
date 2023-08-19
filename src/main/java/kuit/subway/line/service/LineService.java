package kuit.subway.line.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.dto.request.LineRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.repository.LineRepository;
import kuit.subway.station.domain.Station;
import kuit.subway.station.dto.response.StationResponse;
import kuit.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Station> stations = extractStationsFrom(request);

        Line line = request.toEntity(stations);

        lineRepository.save(line);
        return LineCreateResponse.of(line);
    }

    public LineResponse showLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        List<StationResponse> stationResponses = line.getStations().stream()
                .map(StationResponse::of)
                .toList();

        return LineResponse.of(line, stationResponses);
    }

    @Transactional
    public LineResponse updateLine(Long lineId, LineRequest request) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        validateDuplicatedStations(request);
        List<Station> stations = extractStationsFrom(request);

        line.updateInfo(request.getName(), request.getColor(), request.getDistance());
        line.updateStations(stations);

        List<StationResponse> stationResponses = stations.stream().map(StationResponse::of).toList();

        return LineResponse.of(line, stationResponses);
    }

    @Transactional
    public void deleteLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_LINE));

        lineRepository.delete(line);
    }

    private void validateDuplicatedStations(LineRequest request) {
        if (request.getUpStationId().equals(request.getDownStationId())) {
            throw new SubwayException(DUPLICATED_UP_STATION_AND_DOWN_STATION);
        }
    }

    private List<Station> extractStationsFrom(LineRequest request) {
        Station upStation = stationRepository.findById(request.getUpStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        Station downStation = stationRepository.findById(request.getDownStationId())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        return List.of(upStation, downStation);
    }
}