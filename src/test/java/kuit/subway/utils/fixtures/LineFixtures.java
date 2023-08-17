package kuit.subway.utils.fixtures;

import kuit.subway.line.dto.request.LineCreateRequest;

public class LineFixtures {

    public static LineCreateRequest 노선_생성_요청(String color, Long distance, String name, Long downStationId, Long upStationId) {
        return LineCreateRequest.builder()
                .color(color)
                .distance(distance)
                .name(name)
                .downStationId(downStationId)
                .upStationId(upStationId)
                .build();
    }
}
