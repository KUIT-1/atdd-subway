package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.station.dto.request.StationCreateRequest;

import static kuit.subway.utils.RestAssuredUtils.*;

public class StationAcceptanceFixtures {

    private static final String BASE_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철역_생성(StationCreateRequest request) {
        return post(BASE_PATH, request);
    }

    public static ExtractableResponse<Response> 지하철역_조회() {
        return get(BASE_PATH);
    }

    public static ExtractableResponse<Response> 지하철역_삭제(Long stationId) {
        return delete(BASE_PATH + "/{stationId}", stationId);
    }
}
