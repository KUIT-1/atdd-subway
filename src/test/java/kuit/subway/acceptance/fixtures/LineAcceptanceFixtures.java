package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.line.dto.request.LineRequest;

import static kuit.subway.utils.RestAssuredUtils.get;
import static kuit.subway.utils.RestAssuredUtils.post;

public class LineAcceptanceFixtures {

    private static final String BASE_PATH = "/lines";

    public static ExtractableResponse<Response> 노선_생성(LineRequest request) {
        return post(request, BASE_PATH);
    }

    public static ExtractableResponse<Response> 노선_조회(Long lineId) {
        return get(BASE_PATH+"/{lineId}", lineId);
    }
}
