package kuit.subway.line.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.global.exception.SubwayException;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kuit.subway.global.exception.CustomExceptionStatus.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public void removeSection(Station station) {
        Optional<Section> upSectionOptional = findUpSection(station);
        Optional<Section> downSectionOptional = findDownSection(station);

        validateRemovableStation(upSectionOptional, downSectionOptional);

        if (upSectionOptional.isPresent() && downSectionOptional.isPresent()) {
            Section upSection = upSectionOptional.get();
            Section downSection = downSectionOptional.get();
            Long distance = upSection.getDistance() + downSection.getDistance();

            upSection.changeDistance(distance);
            upSection.changeDownStation(downSection.getDownStation());
            sections.remove(downSection);
            return;
        }

        // 상행종점 제거
        if (upSectionOptional.isPresent()) {
            Section section = upSectionOptional.get();
            sections.remove(section);
            return;
        }

        // 하행종점 제거
        Section section = downSectionOptional.get();
        sections.remove(section);
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

    private void validateRemovableStation(Optional<Section> upSectionOptional, Optional<Section> downSectionOptional) {
        if (sections.size() == 1) {
            throw new SubwayException(UNDER_MINIMUM_SECTION_SIZE);
        }

        if (upSectionOptional.isEmpty() && downSectionOptional.isEmpty()) {
            throw new SubwayException(NOT_EXISTED_STATION_IN_SECTION);
        }
    }

    public Long getTotalDistance() {
        return sections.stream()
                .mapToLong(Section::getDistance)
                .sum();
    }
}
