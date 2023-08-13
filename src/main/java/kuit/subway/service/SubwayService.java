package kuit.subway.service;

import kuit.subway.domain.Subway;
import kuit.subway.repository.SubwayRepository;
import kuit.subway.response.GetStationsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubwayService {
    private final SubwayRepository subwayRepository;

    @Transactional
    public Subway createStation(String name) {
        return subwayRepository.save(new Subway(name));
    }

    public List<GetStationsResponse> getStations() {
        return subwayRepository.findAll().stream().map(
                subway -> {
                    return new GetStationsResponse(subway.getId(), subway.getName());
                }
        ).collect(Collectors.toList());
    }

    public void deleteStation(Long id) {
        subwayRepository.deleteById(id);
    }
}
