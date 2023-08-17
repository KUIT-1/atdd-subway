package kuit.subway.line.dto.request;

import jakarta.validation.constraints.Size;
import kuit.subway.line.domain.Line;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineCreateRequest {

    @Size(min = 2, max = 10)
    private String name;
    @Size(min = 2, max = 10)
    private String color;
    private Long distance;

    private Long downStationId;
    private Long upStationId;

    public Line toEntity() {
        return Line.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }
}
