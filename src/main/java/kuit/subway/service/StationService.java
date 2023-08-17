package kuit.subway.service;

import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import kuit.subway.utils.exception.StationException;
import kuit.subway.request.station.StationRequest;
import kuit.subway.response.station.CreateStationResponse;
import kuit.subway.response.station.ShowStationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kuit.subway.utils.BaseResponseStatus.DUPLICATED_STATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationService {
    private final StationRepository stationRepository;

    @Transactional
    public CreateStationResponse createStation(StationRequest request) {
        String name = request.getName();

        if(stationRepository.existsByName(name))
            throw new StationException(DUPLICATED_STATION);

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
