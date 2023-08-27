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
            changeUpSection(section);
            changeDownSection(section);
        }
        this.sections.add(section);
    }

    private boolean isFirstOrLastStation(Section section) {
        if (!sections.isEmpty()) {
            return section.getUpStation().equals(findLastSection().get().getDownStation())
                    || section.getDownStation().equals(findFirstSection().get().getUpStation());
        }
        return true;
    }

    private Optional<Section> findFirstSection() {
        Optional<Section> firstSection = sections.stream().filter(section ->
                        sections.stream().noneMatch(existedSection ->
                                section.getUpStation().equals(existedSection.getDownStation())))
                .findFirst();
        return firstSection;
    }

    private Optional<Section> findLastSection() {
        Optional<Section> lastSection = sections.stream().filter(section ->
                        sections.stream().noneMatch(existedSection ->
                                section.getDownStation().equals(existedSection.getUpStation())))
                .findFirst();
        return lastSection;
    }

    // 역 사이에 낀 경우
    private void changeUpSection(Section section) {
        sections.stream().filter(existedSection -> existedSection.getUpStation().equals(section.getUpStation()))
                .findFirst()
                .ifPresent(existedSection -> {
                    validateSectionDistance(section, existedSection);
                    existedSection.changeUpStation(section.getDownStation());
                });
    }

    private void changeDownSection(Section section) {
        sections.stream().filter(existedSection -> existedSection.getDownStation().equals(section.getDownStation()))
                .findFirst()
                .ifPresent(existedSection -> {
                    validateSectionDistance(section, existedSection);
                    existedSection.changeDownStation(section.getUpStation());
                });
    }

    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        Section firstSection = findFirstSection().get();
        Station upStation = firstSection.getUpStation();
        Station downStation = null;

        stations.add(upStation);

        Optional<Section> section = findDownSection(upStation);

        while (section.isPresent()) {
            downStation = section.get().getDownStation();
            stations.add(downStation);
            section = findDownSection(downStation);
        }

        return stations;
    }

    private Optional<Section> findUpSection(Station station) {
        return sections.stream()
                .filter(section -> section.getDownStation().equals(station))
                .findFirst();
    }

    private Optional<Section> findDownSection(Station station) {
        return sections.stream()
                .filter(section -> section.getUpStation().equals(station))
                .findFirst();
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
                section.getUpStation().equals(existedSection.getUpStation()) || section.getUpStation().equals(existedSection.getDownStation()));
    }

    private boolean isExistedDownStation(Section section) {
        return sections.stream().anyMatch(existedSection ->
                section.getDownStation().equals(existedSection.getUpStation()) || section.getDownStation().equals(existedSection.getDownStation()));
    }

    private void validateSectionDistance(Section section, Section existedSection) {
        if (section.getDistance() >= existedSection.getDistance()) {
            throw new SubwayException(EXCEED_DISTANCE);
        }
    }
}
