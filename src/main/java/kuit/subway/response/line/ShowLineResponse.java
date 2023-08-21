package kuit.subway.response.line;


import kuit.subway.domain.Line;
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
    private ShowLineResponse(Long id, String name, Station upStation, Station downStation, String color){
        this.id = id;
        this.name = name;
        this.stations.add(ShowStationResponse.from(upStation));
        this.stations.add(ShowStationResponse.from(downStation));
        this.color = color;
    }

    public static ShowLineResponse from(Line line){
        return new ShowLineResponse(line.getId(), line.getName(), line.getUpStation(), line.getDownStation(), line.getColor());
    }
}
