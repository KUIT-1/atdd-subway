package kuit.subway.service;

import kuit.subway.dto.station.request.StationCreateRequestDto;
import kuit.subway.dto.station.response.StationCreateResponseDto;
import kuit.subway.dto.station.response.StationResponseDto;
import kuit.subway.entity.Station;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationCreateResponseDto createStation(StationCreateRequestDto requestDto) {
        validateDuplicatedName(requestDto);

        Station station = stationRepository.save(requestDto.toEntity());

        return StationCreateResponseDto.of(station);
    }

    public List<StationResponseDto> showStations() {
        List<Station> stations = stationRepository.findAll();
        List<StationResponseDto> responseDtos = stations.stream()
                .map(StationResponseDto::of)
                .collect(Collectors.toList());

        return responseDtos;
    }

    @Transactional
    public void deleteStation(Long stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 역입니다. id=" + stationId));

        stationRepository.delete(station);
    }

    private void validateDuplicatedName(StationCreateRequestDto requestDto) {
        if(stationRepository.existsByName(requestDto.getName())){
            throw new IllegalArgumentException("이미 존재하는 역입니다. station=" + requestDto.getName());
        }
    }
}
