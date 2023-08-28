package kuit.subway.repository;

import kuit.subway.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query( "select s from Section s " +
            "where s.line.id = :lineId and " +
            "s.downStation.id = :downStationId and s.upStation.id = :upStationId")
    Section findByLineIdAndStationsId(@Param("lineId") Long lineId, @Param("upStationId") Long upStationId, @Param("downStationId") Long downStationId);
}
