package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static kuit.subway.utils.RestAssuredUtils.getPath;

public class PathAcceptanceFixtures {

    private static final String BASE_PATH = "/paths";

    public static ExtractableResponse<Response> 경로_조회(Long sourceStationId, Long targetStationId) {
        return getPath(BASE_PATH, sourceStationId, targetStationId);
    }
}
