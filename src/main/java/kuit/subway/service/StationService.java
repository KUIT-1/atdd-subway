package kuit.subway.service;

import kuit.subway.domain.Station;
import kuit.subway.dto.response.CreateStationResponse;
import kuit.subway.dto.response.StationDto;
import kuit.subway.dto.response.StationListResponse;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StationService {
    private final StationRepository stationRepository;

    @Transactional
    public CreateStationResponse createOne(String name) {
        Station savedStation = stationRepository.save(
                Station.builder()
                        .name(name)
                        .build());
        return new CreateStationResponse(savedStation.getId());
    }

    public StationListResponse getStations() {
        List<Station> savedStations = stationRepository.findAll();
        List<StationDto> stations = savedStations.stream().map(
                StationDto::from
        ).toList();
        return new StationListResponse(stations);
    }

    @Transactional
    public Long deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 지하철 역을 찾을 수 없습니다."));
        stationRepository.delete(station);
        return id;
    }
}
