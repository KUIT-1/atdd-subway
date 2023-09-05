package kuit.subway.line.dto.response;

import kuit.subway.line.domain.Line;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LineCreateResponse {

    private Long id;

    public static LineCreateResponse of(Line line) {
        return LineCreateResponse.builder()
                .id(line.getId())
                .build();
    }
}
