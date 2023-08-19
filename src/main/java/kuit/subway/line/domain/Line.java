package kuit.subway.line.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import kuit.subway.BaseTimeEntity;
import kuit.subway.global.exception.SubwayException;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static kuit.subway.global.exception.CustomExceptionStatus.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String color;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    @Builder
    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addSection(Section section) {
        validateAvailableSection(section);
        this.sections.add(section);
    }

    // TODO 리팩토링 필수
    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        boolean isFirst = true;
        for (Section section : sections) {
            if (isFirst) {
                stations.add(section.getUpStation());
                stations.add(section.getDownStation());
            } else {
                stations.add(section.getDownStation());
            }
            isFirst = false;
        }
        return stations;
    }

    public void removeSection() {
        if (sections.size() == 1) {
            throw new SubwayException(CANNOT_REMOVE_SECTION);
        }
        sections.remove(sections.size() - 1);
    }

    private void validateAvailableSection(Section section) {
        // 처음 역을 만드는 경우
        if (sections.size() == 0) {
            return;
        }
        validateExistedDownStation(section);
        validateCycleInDownStation(section);
    }

    // 새로운 구간의 상행역은 등록되어있는 하행 종점역이어야 한다.
    private void validateExistedDownStation(Section section) {
        Section lastSection = sections.get(sections.size() - 1);
        if (!lastSection.getDownStation().getId().equals(section.getUpStation().getId())) {
            throw new SubwayException(INVALID_SECTION_NOT_EXISTED_DOWN_STATION);
        }
    }

    // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
    private void validateCycleInDownStation(Section section) {
        boolean hasCycle = sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation() == section.getDownStation() || existedSection.getDownStation() == section.getDownStation());

        if (hasCycle) {
            throw new SubwayException(INVALID_SECTION_CANNOT_CYCLE);
        }
    }
}
