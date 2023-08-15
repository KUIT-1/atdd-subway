package kuit.subway.repository;

import kuit.subway.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {
    boolean existsByName(String name);
}
