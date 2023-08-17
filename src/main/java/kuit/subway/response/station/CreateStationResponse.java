package kuit.subway.response;

import kuit.subway.domain.Station;
import lombok.Getter;

@Getter
public class CreateStationResponse {
    private Long id;

    private CreateStationResponse(Long id){
        this.id = id;
    }

    public static CreateStationResponse from(Station station){
        return new CreateStationResponse(station.getId());
    }
}
