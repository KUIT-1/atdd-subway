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
    Long distance;

    @Positive
    Long downStationId;

    @Positive
    Long upStationId;
}
