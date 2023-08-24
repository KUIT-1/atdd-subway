package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.utils.BaseResponseStatus;
import kuit.subway.utils.exception.StationException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
        this.sections.add(section);
    }

    public void remove(Section section) {
        this.sections.remove(section);
    }

    public int size(){
        return this.sections.size();
    }


    public Station getLastDownStation() {
        if(this.sections.isEmpty()){
            throw new StationException(BaseResponseStatus.NONE_STATION);
        }
        int lastIdx = this.sections.size() - 1;
        return this.sections.get(lastIdx).getDownStation();
    }

    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<>();

        sections.stream().map(
                section -> stations.add(section.getUpStation()));

        stations.add(getLastDownStation());
        return stations;
    }

    public boolean hasStation(Long stationId) {
        return sections.stream()
                .anyMatch(section -> section.getUpStation().getId().equals(stationId)
                        || section.getDownStation().getId().equals(stationId)
        );
    }

}
