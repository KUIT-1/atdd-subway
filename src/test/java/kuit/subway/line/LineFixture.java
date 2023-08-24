package kuit.subway.line;

import java.util.Map;

import static kuit.subway.line.LineStep.지하철_노선_바디_생성;
import static kuit.subway.line.LineStep.지하철_노선_생성_요청;
import static kuit.subway.station.StationStep.지하철_역_생성_요청;

public class LineFixture {
    public static final String ID_PATH = "result.id";
    public static final String GREEN = "green";
    public static final String 이호선 = "2호선";
    public static final String TEN = "10";

    public static ExtractableResponse<Response> 지하철_2호선_생성_Fixture(String upStationName, String downStationName){
        return 지하철_노선_생성_Fixture(GREEN, TEN, 이호선, downStationName, upStationName);

    public static void 지하철_2호선_생성(){
        지하철_역_생성_요청("성수역");
        지하철_역_생성_요청("강남역");
        Map<String, String> body = 지하철_노선_바디_생성("green", "10", "2호선", "2", "1");
        지하철_노선_생성_요청(body);
    }
    private static ExtractableResponse<Response> 지하철_노선_생성_Fixture(String color, String distance, String lineName, String upStationName, String downStationName){
        Long upId = 지하철_역_생성_요청(upStationName).jsonPath().getLong(ID_PATH);
        Long downId = 지하철_역_생성_요청(downStationName).jsonPath().getLong(ID_PATH);
        Map<String, String> body = 지하철_노선_바디_생성(color, distance, lineName, Long.toString(downId), Long.toString(upId));
        return 지하철_노선_생성_요청(body);
    }
}
