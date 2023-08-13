package kuit.subway.dto.station.response;

import kuit.subway.entity.Station;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StationResponseDto {

    private Long id;
    private String name;

    public static StationResponseDto of(Station station) {
        return StationResponseDto.builder()
                .id(station.getId())
                .name(station.getName())
                .build();
    }
}
