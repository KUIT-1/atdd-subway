package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public CreateLineResponse addLine(CreateLineRequest res) {

        // 존재하지 않는 station_id를 추가하려고 하면 예외발생
        stationRepository.findById(res.getDownStationId()).orElseThrow(EntityNotFoundException::new);
        stationRepository.findById(res.getUpStationId()).orElseThrow(EntityNotFoundException::new);

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = new Line(res.getColor(), res.getDistance(), res.getName(), res.getDownStationId(), res.getUpStationId());
        lineRepository.save(line);

        return new CreateLineResponse(line.getId());
    }

    @Transactional
    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        
        // 존재하지 않는 역일 때도 예외처리
        Station downStation = stationRepository.findById(line.getDownStationId()).orElseThrow(EntityNotFoundException::new);
        Station upStation = stationRepository.findById(line.getUpStationId()).orElseThrow(EntityNotFoundException::new);

        List<Station> stations = new ArrayList<>();
        stations.add(downStation);
        stations.add(upStation);


        LineDto result = LineDto.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(stations)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        return result;
    }


}
