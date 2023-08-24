package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.request.line.UpdateLineRequest;
import kuit.subway.utils.exception.LineException;
import lombok.*;

import java.util.Objects;

import static kuit.subway.utils.BaseResponseStatus.*;

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

    @Column(length = 20, nullable = false)
    private String name;

    @Builder.Default
    @Embedded
    private Sections sections = new Sections();

    public void update(UpdateLineRequest request){
        this.name = request.getName();
        this.color = request.getColor();
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }

    public Station getLastDownStation() {
        return this.sections.getLastDownStation();
    }

    public void deleteSection(Section section) {
        this.sections.remove(section);
    }


    private final static String DOWN = "down";
    private final static String UP = "up";

    public void isSectionRegistrable(Long upStationId, Long downStationId) {
        checkStationIsLastDownStation(upStationId, UP);
        checkDownStationIsNonexistent(downStationId);
    }

    public void isSectionDeletable(Long downStationId){
        checkStationIsLastDownStation(downStationId, DOWN);
        isSingleSection();
    }

    private void isSingleSection() {
        if(sections.size() <= 1){
            throw new LineException(CANNOT_DELETE_SECTION);
        }
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
