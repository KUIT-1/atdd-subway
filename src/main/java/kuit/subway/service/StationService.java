package kuit.subway.service;

import kuit.subway.dto.station.request.StationCreateRequest;
import kuit.subway.dto.station.response.StationCreateResponse;
import kuit.subway.dto.station.response.StationResponse;
import kuit.subway.entity.Station;
import kuit.subway.exception.SubwayException;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kuit.subway.exception.CustomExceptionStatus.DUPLICATED_STATION;
import static kuit.subway.exception.CustomExceptionStatus.NOT_EXISTED_STATION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationCreateResponse createStation(StationCreateRequest requestDto) {
        validateDuplicatedName(requestDto);

        Station station = stationRepository.save(requestDto.toEntity());

        return StationCreateResponse.of(station);
    }

    public List<StationResponse> showStations() {
        List<Station> stations = stationRepository.findAll();
        List<StationResponse> responseDtos = stations.stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());

        return responseDtos;
    }

    @Transactional
    public void deleteStation(Long stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_STATION));

        stationRepository.delete(station);
    }

    private void validateDuplicatedName(StationCreateRequest requestDto) {
        if(stationRepository.existsByName(requestDto.getName())){
            throw new SubwayException(DUPLICATED_STATION);
        }
    }
}
