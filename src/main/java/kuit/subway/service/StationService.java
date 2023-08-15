package kuit.subway.service;

import jakarta.persistence.EntityExistsException;
import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import kuit.subway.response.station.GetStationsResponse;
import kuit.subway.response.station.PostStationResponse;
import kuit.subway.utils.exception.StationException;
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
    public PostStationResponse createStation(String name) {
        if(stationRepository.findByName(name).isPresent())
            throw new StationException(DUPLICATED_STATION);

        Long id = stationRepository.save(new Station(name)).getId();
        return new PostStationResponse(id);
    }

    public List<GetStationsResponse> getStations() {
        return stationRepository.findAll().stream().map(
                station -> new GetStationsResponse(station.getId(), station.getName())
        ).toList();
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
