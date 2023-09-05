package kuit.subway.station.dto.response;

import kuit.subway.station.domain.Station;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StationResponse {

    private Long id;
    private String name;

    public static StationResponse of(Station station) {
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .build();
    }
}
