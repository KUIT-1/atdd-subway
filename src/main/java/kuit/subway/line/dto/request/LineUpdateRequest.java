package kuit.subway.line.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpdateRequest {

    @Size(min = 2, max = 10)
    private String name;
    @Size(min = 2, max = 10)
    private String color;
}
