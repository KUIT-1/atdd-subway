package kuit.subway.utils.fixtures;

import kuit.subway.station.dto.request.StationCreateRequest;

public class StationFixtures {

    public static StationCreateRequest 지하철역_생성_요청(String name) {
        return StationCreateRequest.builder()
                .name(name)
                .build();
    }
}
