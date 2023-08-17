package kuit.subway.response;

import kuit.subway.domain.Station;
import lombok.Getter;

@Getter
public class ShowStationResponse {
    private Long id;
    private String name;

    private ShowStationResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static ShowStationResponse from(Station station){
        return new ShowStationResponse(station.getId(), station.getName());
    }

}
