package kuit.subway.request.section;


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
    private Long distance;

    @Positive
    private Long downStationId;

    @Positive
    private Long upStationId;
}
