package kuit.subway.section;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.request.section.SectionRequest;
import org.junit.jupiter.api.Test;

import static kuit.subway.line.LineFixture.지하철_2호선_생성_Fixture;
import static kuit.subway.section.SectionStep.지하철_구간_등록_요청;
import static kuit.subway.station.StationFixture.*;
import static kuit.subway.station.StationStep.지하철_역_생성_요청;
import static kuit.subway.utils.BaseResponseStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SectionTest extends AcceptanceTest {
    private static final String ID_PATH = "result.id";
    private static final String RESPONSECODE = "responseCode";
    @Test
    void 노선에_구간_등록_요청_테스트() {
        // given
        지하철_2호선_생성_Fixture(성수역, 강남역);
        지하철_역_생성_요청(교대역);

        // when
        SectionRequest request = new SectionRequest(10L, 3L, 2L);
        ExtractableResponse<Response> response = 지하철_구간_등록_요청("1", request);

        // then
        assertEquals(1L, response.jsonPath().getLong(ID_PATH));
        assertEquals(200, response.statusCode());
        assertEquals(REGISTERED_SUCCESS.getResponseCode(), response.jsonPath().getLong(RESPONSECODE));
    }

    @Test
    void 노선에_구간_등록_요청_테스트_WHEN_상행역_오류() {
        // given
        지하철_2호선_생성_Fixture(성수역, 강남역);
        지하철_역_생성_요청(교대역);

        /* when
           새로운 구간의 상행역이 해당 노선에 등록되어있는 하행 종점역이 아닌 경우
         */
        SectionRequest request = new SectionRequest(10L, 3L, 1L);
        ExtractableResponse<Response> response = 지하철_구간_등록_요청("1", request);

        // then
        assertEquals(400, response.statusCode());
        assertEquals(ONLY_LAST_DOWNSTATION_REGISTER_ALLOWED.getResponseCode(), response.jsonPath().getLong(RESPONSECODE));
    }

    @Test
    void 노선에_구간_등록_요청_테스트_WHEN_존재하는_역() {
        // given
        지하철_2호선_생성_Fixture(성수역, 강남역);

        /* when
           새로운 구간의 하행역이 해당 노선에 등록되어있는 역인 경우 등록 불가
         */
        SectionRequest request = new SectionRequest(10L, 1L, 2L);
        ExtractableResponse<Response> response = 지하철_구간_등록_요청("1", request);

        // then
        assertEquals(400, response.statusCode());
        assertEquals(ALREADY_REGISTERED_STATION.getResponseCode(), response.jsonPath().getLong(RESPONSECODE));
    }

}
