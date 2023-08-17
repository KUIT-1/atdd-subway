package kuit.subway.utils.fixtures;

import kuit.subway.line.dto.request.LineRequest;

public class LineFixtures {

    public static LineRequest 노선_요청(String name, String color, Long distance, Long downStationId, Long upStationId) {
        return LineRequest.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .downStationId(downStationId)
                .upStationId(upStationId)
                .build();
    }
}
