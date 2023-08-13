package kuit.subway.service;

import kuit.subway.domain.Subway;
import kuit.subway.repository.SubwayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubwayService {
    private final SubwayRepository subwayRepository;

    @Transactional
    public Subway createStation(String name) {
        return subwayRepository.save(new Subway(name));
    }
}
