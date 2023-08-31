package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.utils.exception.LineException;
import kuit.subway.utils.exception.StationException;
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
        this.sections.add(section);
    }

    // TODO : 중간구간 추가 시 엄청 바뀔..! distance 등등.. 수정되어야 함.
    public void removeStation(Station station) {
        Optional<Section> section = Optional.ofNullable(findSectionByDownStation(station)
                .orElseThrow(() -> new LineException(NONE_SECTION)));
        if(section.isPresent())
            sections.remove(section.get());
    }

    private Optional<Section> findSectionByDownStation(Station station) {
        return sections.stream().filter(
                section -> section.getDownStation().getId().equals(station.getId()))
                .findFirst();
    }

    private void isSectionRegistrable(Long upStationId, Long downStationId) {
        areBothStationsRegistered(upStationId, downStationId);
        checkAnyStationIsTerminalStation(upStationId, downStationId);
    }


    private void checkAnyStationIsTerminalStation(Long upStationId, Long downStationId){
        Station lastDownStation = getLastSection().get().getDownStation();
        Station firstUpStation = getFirstSection().get().getUpStation();
        boolean upStationIsLastDown = Objects.equals(lastDownStation.getId(), upStationId);
        boolean downStationIsFirstUp = Objects.equals(firstUpStation.getId(), downStationId);


        if(!upStationIsLastDown && !downStationIsFirstUp){
            throw new LineException(ONLY_TERMINAL_STATION_REGISTER_ALLOWED);
        }
    }

    private void areBothStationsRegistered(Long upStationId, Long downStationId) {
        boolean downIsExist = hasStation(downStationId);
        boolean upIsExist = hasStation(upStationId);
        if(downIsExist && upIsExist) throw new LineException(ALREADY_REGISTERED_SECTION);
    }

    private Optional<Section> getFirstSection() {
        if(this.sections.isEmpty()){
            throw new StationException(NONE_STATION);
        }

        return sections.stream()
                .filter(section -> sections.stream()
                        .noneMatch(section1 -> section1.getDownStation().getId()
                                                .equals(section.getUpStation().getId())))
                .findFirst();
    }

    private Optional<Section> getLastSection() {
        if(this.sections.isEmpty()){
            throw new LineException(EMPTY_LINE);
        }

        return sections.stream()
                .filter(section -> sections.stream()
                        .noneMatch(section1 -> section1.getUpStation().getId()
                                                .equals(section.getDownStation().getId())))
                .findFirst();
    }

    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<>();

        Section firstSection = getFirstSection().get(); // EMPTY_LINE : getFirstSection에서 예외처리
        Station upStation = firstSection.getUpStation();
        Station downStation;
        stations.add(upStation);

        Optional<Section> nextSection = findNextSection(upStation);
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
        checkStationIsLastStation(stationId);
        isSingleSection();
    }

    private void checkStationIsLastStation(Long stationId){
        Station lastDownStation = getLastSection().get().getDownStation();
        if(!Objects.equals(lastDownStation.getId(), stationId)){
            System.out.println(lastDownStation.getId());
            System.out.println(stationId);
            throw new LineException(ONLY_LAST_SECTION_DELETION_ALLOWED);
        }
    }

    private void isSingleSection() {
        if(sections.size() <= 1){
            throw new LineException(CANNOT_DELETE_SECTION);
        }
    }

}
