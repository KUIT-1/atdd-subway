package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDeleteResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.LineUpdateResponse;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public LineCreateResponse addLine(LineCreateRequest res) {

        // 존재하지 않는 station_id를 추가하려고 하면 예외발생
        validateStations(res.getDownStationId(), res.getUpStationId());

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = new Line(res.getColor(), res.getDistance(), res.getName(), res.getDownStationId(), res.getUpStationId());
        lineRepository.save(line);

        return new LineCreateResponse(line.getId());
    }

    @Transactional
    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        LineDto result = LineDto.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        return result;
    }

    @Transactional
    public List<LineDto> findAllLines() {

        List<Line> findLines = lineRepository.findAll();
        List<LineDto> result = findLines.stream()
                .map(line -> LineDto.builder()
                        .id(line.getId())
                        .name(line.getName())
                        .color(line.getColor())
                        .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public LineUpdateResponse updateLine(Long id, LineCreateRequest req) {
        // 존재하지 않는 노선을 수정하려 했을때 예외처리
        Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // 존재하지 않는 station_id로 변경하려 했을 때 예외처리
        validateStations(req.getDownStationId(), req.getUpStationId());

        // 존재한다면, request 대로 노선 수정
        line.setColor(req.getColor());
        line.setDistance(req.getDistance());
        line.setName(req.getName());
        line.setDownStationId(req.getDownStationId());
        line.setUpStationId(req.getUpStationId());

        return LineUpdateResponse.builder()
                .color(req.getColor())
                .distance(req.getDistance())
                .name(req.getName())
                .downStationId(req.getDownStationId())
                .upStationId(req.getUpStationId()).build();
    }

    @Transactional
    public LineDeleteResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = lineRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        lineRepository.delete(line);
        return new LineDeleteResponse(line.getId());
    }

    // 노선의 역 리스트 생성 함수
    private List<Station> createStationList(Long downStationId, Long upStationId) {
        Station downStation = stationRepository.findById(downStationId).get();
        Station upStation = stationRepository.findById(upStationId).get();

        List<Station> stations = new ArrayList<>();
        stations.add(downStation);
        stations.add(upStation);
        return stations;
    }

    // 존재하지 않는 지하철 역 조회 시 예외처리
    private void validateStations(Long downStationId, Long upStationId) {
        stationRepository.findById(downStationId).orElseThrow(EntityNotFoundException::new);
        stationRepository.findById(upStationId).orElseThrow(EntityNotFoundException::new);
    }

}
