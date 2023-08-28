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
import static kuit.subway.utils.BaseResponseStatus.ALREADY_REGISTERED_STATION;

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
        checkUpStationIsLastDownStation(upStationId);
        checkDownStationIsNonexistent(downStationId);
    }


    private void checkUpStationIsLastDownStation(Long upStationId){
        Station lastDownStation = getLastSection().getDownStation();
        if(!Objects.equals(lastDownStation.getId(), upStationId)){
            throw new LineException(ONLY_LAST_DOWNSTATION_REGISTER_ALLOWED);
        }
    }

    private void checkDownStationIsNonexistent(Long downStationId) {
        boolean isExist = hasStation(downStationId);
        if(isExist) throw new LineException(ALREADY_REGISTERED_STATION);
    }


    private Section getLastSection() {
        if(this.sections.isEmpty()){
            throw new LineException(EMPTY_LINE);
        }
        int lastIdx = this.sections.size() - 1;
        return this.sections.get(lastIdx);
    }

    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<>();

        sections.stream().map(
                section -> stations.add(section.getUpStation()));

        stations.add(getLastSection().getDownStation());
        return stations;
    }

    private boolean hasStation(Long stationId) {
        return sections.stream()
                .anyMatch(section -> section.getUpStation().getId().equals(stationId)
                        || section.getDownStation().getId().equals(stationId)
        );
    }

}
