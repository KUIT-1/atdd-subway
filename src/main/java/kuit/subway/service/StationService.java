package kuit.subway.service;

import jakarta.persistence.EntityExistsException;
import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import kuit.subway.response.GetStationsResponse;
import kuit.subway.response.PostStationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationService {
    private final StationRepository stationRepository;

    @Transactional
    public PostStationResponse createStation(String name) {
        if(stationRepository.findByName(name).isPresent())
            throw new EntityExistsException(name + "은 이미 존재합니다.");
        Long id = stationRepository.save(new Station(name)).getId();
        return new PostStationResponse(id);
    }

    public List<GetStationsResponse> getStations() {
        return stationRepository.findAll().stream().map(
                station -> {
                    return new GetStationsResponse(station.getId(), station.getName());
                }
        ).collect(Collectors.toList());
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
