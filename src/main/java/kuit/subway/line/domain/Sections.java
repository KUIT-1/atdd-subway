package kuit.subway.line.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.global.exception.SubwayException;
import kuit.subway.station.domain.Station;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
        validateLastDownStation(section);
        validateDuplicatedSection(section);
    }

    // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
    private void validateLastDownStation(Section section) {
        if (section.getDownStation().equals(findLastDownStation())) {
            throw new SubwayException(INVALID_DOWN_STATION);
        }
    }

    // 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가 불가
    private void validateDuplicatedSection(Section section) {
        sections.stream().filter(existedSection ->
                existedSection.getUpStation().equals(section.getUpStation()) && existedSection.getDownStation().equals(section.getDownStation()))
                .findFirst()
                .ifPresent(existedSection -> {
                    throw new SubwayException(DUPLICATED_SECTION);
                });
    }

    private void validateSectionDistance(Section section, Section existedSection) {
        if (section.getDistance() >= existedSection.getDistance()) {
            throw new SubwayException(EXCEED_DISTANCE);
        }
    }
}
