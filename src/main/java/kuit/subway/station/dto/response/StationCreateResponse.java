package kuit.subway.station.dto.response;

import kuit.subway.station.domain.Station;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StationCreateResponse {

    private Long id;

    public static StationCreateResponse of(Station station) {
        return StationCreateResponse.builder()
                .id(station.getId())
                .build();
    }
}
