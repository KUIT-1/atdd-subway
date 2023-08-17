package kuit.subway.utils.fixtures;

import kuit.subway.line.dto.request.LineCreateRequest;

public class LineFixtures {

    public static LineCreateRequest 노선_생성_요청(String name, String color, Long distance, Long downStationId, Long upStationId) {
        return LineCreateRequest.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .downStationId(downStationId)
                .upStationId(upStationId)
                .build();
    }
}
