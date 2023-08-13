package kuit.subway.repository;

import kuit.subway.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwayRepository extends JpaRepository<Subway, Long> {

    Optional<Station> findByName(String name);
}
