package kuit.subway.path.domain;

import kuit.subway.line.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
@Builder
@AllArgsConstructor
public class SectionEdge extends DefaultWeightedEdge {

    private Section section;

    public static SectionEdge of(Section section) {
        return SectionEdge.builder()
                .section(section)
                .build();
    }
}
