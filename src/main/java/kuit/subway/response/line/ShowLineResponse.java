package kuit.subway.response.line;


import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Sections;
import kuit.subway.domain.Station;
import kuit.subway.response.station.ShowStationResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShowLineResponse {
    private Long id;
    private String name;
    private String color;
    private List<ShowStationResponse> stations = new ArrayList<>();

    private ShowLineResponse(Long id, String name, Sections sectionList, String color){
        this.id = id;
        this.name = name;
        this.stations = fromSection(sectionList);
        this.color = color;
    }

    public static ShowLineResponse from(Line line){
        return new ShowLineResponse(line.getId(), line.getName(), line.getSections(), line.getColor());
    }

    private List<ShowStationResponse> fromSection(Sections sectionList){
        List<Station> stations = sectionList.getStationList();

        return stations.stream().map(ShowStationResponse::from).toList();
    }
}
