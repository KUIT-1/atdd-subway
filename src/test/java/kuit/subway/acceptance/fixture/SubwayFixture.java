package kuit.subway.acceptance.fixture;

import kuit.subway.dto.station.request.StationCreateRequest;

public class SubwayFixtures {

    public static StationCreateRequest 지하철역_생성_요청(String name) {
        return StationCreateRequest.builder()
                .name(name)
                .build();
    }
}
