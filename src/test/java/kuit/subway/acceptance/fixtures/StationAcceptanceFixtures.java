package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.station.dto.request.StationCreateRequest;

import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;
import static kuit.subway.utils.RestAssuredUtils.*;

public class StationAcceptanceFixtures {

    private static final String BASE_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철역_생성(String name) {
        StationCreateRequest requestDto = 지하철역_생성_요청(name);
        return post(BASE_PATH, requestDto);
    }

    public static ExtractableResponse<Response> 지하철역_조회() {
        return get(BASE_PATH);
    }

    public static ExtractableResponse<Response> 지하철역_삭제() {
        return delete(BASE_PATH + "/{stationId}", 1L);
    }
}
