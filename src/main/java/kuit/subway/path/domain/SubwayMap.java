package kuit.subway.path.domain;

import kuit.subway.line.domain.Line;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class SubwayMap {

    private List<Line> lines;

    private WeightedMultigraph<Station, SectionEdge> generateSubwayMap() {
        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
        initVertex(graph);
        initEdge(graph);

        return graph;
    }

    private void initVertex(WeightedMultigraph<Station, SectionEdge> graph) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void initEdge(WeightedMultigraph<Station, SectionEdge> graph) {
        lines.stream()
                .flatMap(line -> line.getSections().stream())
                .forEach(section -> {
                    SectionEdge edge = SectionEdge.of(section);
                    graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
                    graph.setEdgeWeight(edge, section.getDistance());
                });
    }
}
