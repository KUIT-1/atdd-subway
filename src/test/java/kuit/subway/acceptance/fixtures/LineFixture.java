package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.domain.Line;

import java.util.Map;

import static kuit.subway.acceptance.fixtures.LineStep.지하철_노선_바디_생성;
import static kuit.subway.acceptance.fixtures.LineStep.지하철_노선_생성_요청;
import static kuit.subway.acceptance.fixtures.StationStep.지하철_역_생성_요청;

public class LineFixture {
    public static final String ID_PATH = "result.id";
    public static final String DARKGREEN = "darkgreen";
    public static final String GREEN = "green";
    public static final String 이호선이름 = "2호선";
    public static final String 칠호선이름 = "7호선";
    public static final String 신분당선이름 = "신분당선";
    public static final String TEN = "10";

    public static ExtractableResponse<Response> 지하철_2호선_생성_요청(String upStationName, String downStationName){
        return 지하철_노선_생성_Fixture(GREEN, TEN, 이호선이름, downStationName, upStationName);
    }

    public static ExtractableResponse<Response> 지하철_7호선_생성_요청(String upStationName, String downStationName){
        return 지하철_노선_생성_Fixture(DARKGREEN, TEN, 칠호선이름, downStationName, upStationName);
    }

    private static ExtractableResponse<Response> 지하철_노선_생성_Fixture(String color, String distance, String lineName, String upStationName, String downStationName){
        Long upId = 지하철_역_생성_요청(upStationName).jsonPath().getLong(ID_PATH);
        Long downId = 지하철_역_생성_요청(downStationName).jsonPath().getLong(ID_PATH);
        Map<String, String> body = 지하철_노선_바디_생성(color, distance, lineName, Long.toString(downId), Long.toString(upId));
        return 지하철_노선_생성_요청(body);
    }

    public static Line create_이호선(){
        return Line.builder()
                .id(1L)
                .color(GREEN)
                .name(이호선이름)
                .build();
    }


}
