package kuit.subway.service;

import jakarta.persistence.EntityExistsException;
import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import kuit.subway.request.StationRequest;
import kuit.subway.response.CreateStationResponse;
import kuit.subway.response.ShowStationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationService {
    private final StationRepository stationRepository;

    @Transactional
    public CreateStationResponse createStation(StationRequest request) {
        String name = request.getName();

        if(stationRepository.existsByName(name))
            throw new EntityExistsException(name);

        Station station = stationRepository.save(new Station(name));

        return CreateStationResponse.from(station);
    }

    public List<ShowStationResponse> getStations() {
        return stationRepository.findAll().stream()
                .map(ShowStationResponse::from).toList();
    }

    @Transactional
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
