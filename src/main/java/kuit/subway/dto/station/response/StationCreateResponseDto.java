package kuit.subway.dto.station.response;

import kuit.subway.entity.Station;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StationCreateResponseDto {

    private Long id;

    public static StationCreateResponseDto of(Station station) {
        return StationCreateResponseDto.builder()
                .id(station.getId())
                .build();
    }
}
