package kuit.subway.dto.station.request;

import kuit.subway.entity.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationCreateRequestDto {

    private String name;

    public Station toEntity() {
        return Station.builder()
                .name(name)
                .build();
    }
}
