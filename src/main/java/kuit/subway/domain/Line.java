package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.*;

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

    public void update(String name, String color){
        this.name = name;
        this.color = color;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }

    public void deleteSectionByStation(Station station) {
        this.sections.deleteSection(station);
    }

}
