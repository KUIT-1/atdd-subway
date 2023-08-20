package kuit.subway.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static kuit.subway.line.LineStep.지하철_노선_바디_생성;
import static kuit.subway.line.LineStep.지하철_노선_생성_요청;
import static kuit.subway.station.StationStep.지하철_역_생성_요청;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTest extends AcceptanceTest {
    private final String ID_PATH = "result.id";
    @Test
    void 지하철_노선_생성_요청_테스트() {
        // given
        지하철_역_생성_요청("성수역");
        지하철_역_생성_요청("강남역");

        // when
        Map<String, String> body = 지하철_노선_바디_생성("green", "10", "2호선", "2", "1");
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(body);

        // then
        assertEquals(1L, response.jsonPath().getLong(ID_PATH));
        assertEquals(201, response.statusCode());
    }
}
