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
  
    private ShowLineResponse(Long id, String name, List<ShowStationResponse> stations, String color){
        this.id = id;
        this.name = name;
        this.stations = stations;
        this.color = color;
    }

    public static ShowLineResponse from(Line line){
        List<Station> stations = line.getSections().getStations();
        List<ShowStationResponse> stationResponseList = stations.stream().map(ShowStationResponse::from).toList();

        return new ShowLineResponse(line.getId(), line.getName(), stationResponseList, line.getColor());
    }

}
