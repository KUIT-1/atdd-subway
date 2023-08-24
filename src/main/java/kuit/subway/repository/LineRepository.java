package kuit.subway.repository;

import kuit.subway.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
    boolean existsLineByName(String name);
    boolean existsLineByColor(String color);

    Line findLineByName(String name);
    Line findLineByColor(String color);

    boolean existsLineById(Long id);
}
