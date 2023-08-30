package kuit.subway.path.domain;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.line.domain.Line;
import kuit.subway.line.domain.Section;
import kuit.subway.line.domain.Sections;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;
import java.util.Optional;

import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_PATH;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class SubwayMap {

    private List<Line> lines;

    public Sections findPath(Station sourceStation, Station targetStation) {
        WeightedMultigraph<Station, SectionEdge> graph = generateSubwayMap();
        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        Optional<GraphPath<Station, SectionEdge>> pathOptional = Optional.ofNullable(dijkstraShortestPath.getPath(sourceStation, targetStation));

        if (pathOptional.isEmpty()) {
            throw new SubwayException(NOT_EXISTED_PATH);
        }

        GraphPath<Station, SectionEdge> path = pathOptional.get();
        List<Section> sections = path.getEdgeList().stream()
                .map(SectionEdge::getSection)
                .toList();

        return new Sections(sections);
    }

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
