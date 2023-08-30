package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.line.dto.request.LineRequest;

import static kuit.subway.acceptance.fixtures.StationAcceptanceFixtures.지하철역_생성;
import static kuit.subway.utils.RestAssuredUtils.*;
import static kuit.subway.utils.fixtures.LineFixtures.노선_요청;
import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;

public class LineAcceptanceFixtures {

    private static final String BASE_PATH = "/lines";

    public static ExtractableResponse<Response> 노선_생성(LineRequest request) {
        return post(request, BASE_PATH);
    }

    public static ExtractableResponse<Response> 노선_조회(Long lineId) {
        return get(BASE_PATH+"/{lineId}", lineId);
    }

    public static ExtractableResponse<Response> 노선_변경(Long lineId, LineRequest request) {
        return post(request, BASE_PATH + "/{lineId}", lineId);
    }

    public static ExtractableResponse<Response> 노선_삭제(Long lineId) {
        return delete(BASE_PATH + "/{lineId}", lineId);
    }

    public static void 구호선_생성() {
        지하철역_생성(지하철역_생성_요청("둔촌역"));
        지하철역_생성(지하철역_생성_요청("군자역"));
        노선_생성(노선_요청("5호선", "purple", 4L, 1L, 2L));
    }

    public static void 팔호선_생성() {
        지하철역_생성(지하철역_생성_요청("어대역"));
        노선_생성(노선_요청("7호선", "dark green", 8L, 2L, 3L));
    }

    public static void 이호선_생성() {
        지하철역_생성(지하철역_생성_요청("건대입구역"));
        노선_생성(노선_요청("2호선", "green", 3L, 3L, 4L));
    }

    public static void 부산선_생성() {
        지하철역_생성(지하철역_생성_요청("해운대역"));
        지하철역_생성(지하철역_생성_요청("울산역"));
        노선_생성(노선_요청("부산-울산선", "blue", 7L, 4L, 5L));
    }
}
