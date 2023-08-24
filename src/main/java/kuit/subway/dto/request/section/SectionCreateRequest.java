package kuit.subway.dto.request.section;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SectionCreateRequest {
    private Long upStationId;
    private Long downStationId;
}
