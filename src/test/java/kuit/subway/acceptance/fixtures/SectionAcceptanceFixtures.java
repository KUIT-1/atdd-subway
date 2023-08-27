package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.line.dto.request.SectionRequest;

import static kuit.subway.utils.RestAssuredUtils.delete;
import static kuit.subway.utils.RestAssuredUtils.post;

public class SectionAcceptanceFixtures {

    private static final String BASE_URL = "/lines/{lineId}/sections";

    public static ExtractableResponse<Response> 구간_생성(Long lineId, SectionRequest request) {
        return post(request, BASE_URL, lineId);
    }

    public static ExtractableResponse<Response> 구간_제거(Long lineId) {
        return delete(BASE_URL, lineId);
    }
}