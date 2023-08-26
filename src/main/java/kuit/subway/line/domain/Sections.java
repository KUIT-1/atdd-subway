package kuit.subway.line.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.global.exception.SubwayException;
import kuit.subway.station.domain.Station;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kuit.subway.global.exception.CustomExceptionStatus.*;

@Embeddable
@Slf4j
public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        validateAvailableSection(section);

        if (!isFirstOrLastStation(section)) {
            changeSection(section);
        }
        this.sections.add(section);
    }

    private boolean isFirstOrLastStation(Section section) {
        if (!sections.isEmpty()) {
            return section.getUpStation().equals(findLastDownStation()) || section.getDownStation().equals(findFirstUpStation());
        }
        return true;
    }

    private Station findFirstUpStation() {
        Station station = sections.get(0).getUpStation();

        for (Section section : sections) {
            if (section.getDownStation().equals(station)) {
                station = section.getUpStation();
            }
        }
        return station;
    }

    private Station findLastDownStation() {
        Station station = sections.get(0).getDownStation();

        for (Section section : sections) {
            if (section.getUpStation().equals(station)) {
                station = section.getDownStation();
            }
        }
        return station;
    }

    // 역 사이에 낀 경우
    private void changeSection(Section section) {
        sections.stream().filter(existedSection -> existedSection.getUpStation().equals(section.getUpStation()))
                .findFirst()
                .ifPresent(existedSection -> {
                        validateSectionDistance(section, existedSection);
                        existedSection.changeUpStation(section.getDownStation());
                });
    }

    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        Station upStation = findFirstUpStation();
        Station downStation = null;
        stations.add(upStation);

        Optional<Section> section = findSection(upStation);

        while (section.isPresent()) {
            downStation = section.get().getDownStation();
            stations.add(downStation);
            section = findSection(downStation);
        }

        return stations;
    }

    private Optional<Section> findSection(Station station) {
        return sections.stream()
                .filter(section -> section.getUpStation().equals(station))
                .findFirst();
    }

    public void removeSection() {
        if (sections.size() == 1) {
            throw new SubwayException(CANNOT_REMOVE_SECTION);
        }
        sections.remove(sections.size() - 1);
    }

    private void validateAvailableSection(Section section) {
        if (sections.isEmpty()) {
            return;
        }
        validateDuplicatedSection(section);
    }

    private void validateDuplicatedSection(Section section) {
        if (isExistedUpStation(section) && isExistedDownStation(section)) {
            throw new SubwayException(EXISTED_STATION_IN_SECTIONS);
        }
    }

    private boolean isExistedUpStation(Section section) {
        return sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getUpStation()) || existedSection.getUpStation().equals(section.getDownStation()));
    }

    private boolean isExistedDownStation(Section section) {
        return sections.stream().anyMatch(existedSection ->
                existedSection.getDownStation().equals(section.getUpStation()) || existedSection.getDownStation().equals(section.getDownStation()));
    }

    private void validateSectionDistance(Section section, Section existedSection) {
        if (section.getDistance() >= existedSection.getDistance()) {
            throw new SubwayException(EXCEED_DISTANCE);
        }
    }
}
