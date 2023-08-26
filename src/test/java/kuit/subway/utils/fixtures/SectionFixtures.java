package kuit.subway.utils.fixtures;

import kuit.subway.line.dto.request.SectionRequest;

public class SectionFixtures {

    public static SectionRequest 구간_생성_요청(Long distance, Long upStationId, Long downStationId) {
        return SectionRequest.builder()
                .distance(distance)
                .upStationId(upStationId)
                .downStationId(downStationId)
                .build();
    }
}
