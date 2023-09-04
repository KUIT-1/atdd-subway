package kuit.subway.request.section;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SectionRequest {
    @Positive
    @NotNull(message = "새로운 구간의 거리 필요")
    private Long distance;

    @Positive
    @NotNull(message = "새로운 구간의 하행역 id 필요")
    private Long downStationId;

    @Positive
    @NotNull(message = "새로운 구간의 상행역 id 필요")
    private Long upStationId;
}
