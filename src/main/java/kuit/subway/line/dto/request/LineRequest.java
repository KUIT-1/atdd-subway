package kuit.subway.line.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kuit.subway.line.domain.Line;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineRequest {

    @Size(min = 2, max = 10)
    private String name;
    @Size(min = 2, max = 10)
    private String color;
    @NotNull
    @Min(1)
    private Long distance;

    private Long downStationId;
    private Long upStationId;

    public Line toEntity(List<Station> stations) {
        return Line.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .stations(stations)
                .build();
    }
}
