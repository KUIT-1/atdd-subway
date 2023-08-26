package kuit.subway.utils.fixtures;

import kuit.subway.line.dto.request.LineRequest;
import kuit.subway.line.dto.request.LineUpdateRequest;

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

    public static LineUpdateRequest 노선_변경_요청(String name, String color) {
        return LineUpdateRequest.builder()
                .name(name)
                .color(color)
                .build();
    }
}
