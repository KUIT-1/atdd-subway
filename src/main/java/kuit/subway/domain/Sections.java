package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.utils.exception.LineException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static kuit.subway.utils.BaseResponseStatus.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Sections {
    @Builder.Default
    @OneToMany(mappedBy = "line", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<Section> sections = new ArrayList<>();

    public void add(Section section) {
        if(sections.isEmpty()) {
            this.sections.add(section);
            return;
        }
        isSectionRegistrable(section.getUpStation().getId(), section.getDownStation().getId());

        if(!checkAnyStationIsTerminalStation(section.getUpStation(), section.getDownStation())){
            updateNextSection(section);
            updatePreviousSection(section);
        }

        this.sections.add(section);
    }

    private void updatePreviousSection(Section section) {
        Optional<Section> downStation = findPreviousSection(section.getDownStation());
        if(downStation.isPresent()){
            if(downStation.get().getDistance() <= section.getDistance())
                throw new LineException(INVALID_DISTANCE);
            downStation.get().changeDownStation(section.getUpStation());
            downStation.get().changeDistance(downStation.get().getDistance() - section.getDistance());
        }
    }

    private void updateNextSection(Section section) {
        Optional<Section> upStation = findNextSection(section.getUpStation());
        if(upStation.isPresent()){
            if(upStation.get().getDistance() <= section.getDistance())
                throw new LineException(INVALID_DISTANCE);
            upStation.get().changeUpStation(section.getDownStation());
            upStation.get().changeDistance(upStation.get().getDistance() - section.getDistance());
        }
    }


    public void removeStation(Station station) {
        Optional<Section> sectionByDownStation = findPreviousSection(station);
        Optional<Section> sectionByUpStation = findNextSection(station);

        if(sectionByDownStation.isPresent() && sectionByUpStation.isPresent()){
            Section AtoB = sectionByDownStation.get();
            Section BtoC = sectionByUpStation.get();

            AtoB.changeDownStation(BtoC.getDownStation());
            AtoB.changeDistance(AtoB.getDistance() + BtoC.getDistance());
        }

        if(sectionByUpStation.isPresent())
            this.sections.remove(sectionByUpStation.get());
        else sectionByDownStation.ifPresent(section -> this.sections.remove(section));
    }


    private void isSectionRegistrable(Long upStationId, Long downStationId) {
        areStationsRegistered(upStationId, downStationId);
    }


    private boolean checkAnyStationIsTerminalStation(Station upStation, Station downStation){
        Station lastDownStation = getLastSection().get().getDownStation();
        Station firstUpStation = getFirstSection().get().getUpStation();
        boolean upStationIsLastDown = Objects.equals(lastDownStation.getId(), upStation.getId());
        boolean downStationIsFirstUp = Objects.equals(firstUpStation.getId(), downStation.getId());

        return upStationIsLastDown || downStationIsFirstUp;
    }

    private void areStationsRegistered(Long upStationId, Long downStationId) {
        boolean isDownExist = hasStation(downStationId);
        boolean isUpExist = hasStation(upStationId);
        if(isDownExist && isUpExist) throw new LineException(ALREADY_REGISTERED_SECTION);
        else if(!isDownExist && !isUpExist) throw new LineException(NEITHER_STATIONS_NOT_REGISTERED);
    }

    private Optional<Section> getFirstSection() {
        return sections.stream()
                .filter(section -> sections.stream()
                        .noneMatch(section1 -> section1.getDownStation().getId()
                                                .equals(section.getUpStation().getId())))
                .findFirst();
    }

    private Optional<Section> getLastSection() {
        return sections.stream()
                .filter(section -> sections.stream()
                        .noneMatch(section1 -> section1.getUpStation().getId()
                                                .equals(section.getDownStation().getId())))
                .findFirst();
    }

    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();

        Section firstSection = getFirstSection().get();
        Station upStation = firstSection.getUpStation();
        Station downStation;
        stations.add(upStation);

        Optional<Section> nextSection = Optional.of(firstSection);
        while(nextSection.isPresent()){
            downStation = nextSection.get().getDownStation();
            stations.add(downStation);
            nextSection = findNextSection(downStation);
        }

        return stations;
    }

    private Optional<Section> findNextSection(Station station){
        return sections.stream().filter(
                        section -> section.getUpStation().getId().equals(station.getId()))
                .findFirst();
    }

    private Optional<Section> findPreviousSection(Station station){
        return sections.stream().filter(
                        section -> section.getDownStation().getId().equals(station.getId()))
                .findFirst();
    }

    private boolean hasStation(Long stationId) {
        return sections.stream()
                .anyMatch(section -> section.getUpStation().getId().equals(stationId)
                        || section.getDownStation().getId().equals(stationId)
        );
    }

    public void deleteSection(Station station) {
        isSectionDeletable(station.getId());
        removeStation(station);
    }

    private void isSectionDeletable(Long stationId){
        isSingleSection();
        checkStationIsRegisteredStation(stationId);
    }
    private void checkStationIsRegisteredStation(Long stationId){
        boolean IsExist = hasStation(stationId);
        if(!IsExist) throw new LineException(STATION_NOT_REGISTERED);
    }

    private void isSingleSection() {
        if(sections.size() <= 1){
            throw new LineException(CANNOT_DELETE_SECTION);
        }
    }

}
