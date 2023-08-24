package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.request.line.LineRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

@NoArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private Long distance;

    @Column(length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "down_station_id")
    private Station downStation;
    @Builder.Default
    @Embedded
    private Sections sections = new Sections();


    public void update(LineRequest request, Station upStation, Station downStation){
        this.name = request.getName();
        this.distance = request.getDistance();
        this.color = request.getColor();
        this.upStation = upStation;
        this.downStation = downStation;
    }

    private final static String DOWN = "down";
    private final static String UP = "up";

    public void isSectionRegistrable(Long upStationId, Long downStationId) {
        checkStationIsLastDownStation(upStationId, UP);
        checkDownStationIsNonexistent(downStationId);
    }
    private void checkStationIsLastDownStation(Long stationId, String direction) {
        Station lastDownStation = getLastDownStation();

        if(Objects.equals(direction, UP)
                & !Objects.equals(lastDownStation.getId(), stationId)){
            throw new LineException(ONLY_LAST_DOWNSTATION_REGISTER_ALLOWED);
        }
        if(Objects.equals(direction, DOWN)
                & !Objects.equals(lastDownStation.getId(), stationId)){
            throw new LineException(ONLY_LAST_SECTION_DELETION_ALLOWED);
        }
    }

    private void checkDownStationIsNonexistent(Long downStationId) {
        boolean isExist = sections.hasStation(downStationId);
        if(isExist) throw new LineException(ALREADY_REGISTERED_STATION);
    }
}
