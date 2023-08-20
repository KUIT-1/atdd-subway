package kuit.subway.line;

import java.util.Map;

import static kuit.subway.line.LineStep.지하철_노선_바디_생성;
import static kuit.subway.line.LineStep.지하철_노선_생성_요청;
import static kuit.subway.station.StationStep.지하철_역_생성_요청;

public class LineFixture {

    public static void 지하철_2호선_생성(){
        지하철_역_생성_요청("성수역");
        지하철_역_생성_요청("강남역");
        Map<String, String> body = 지하철_노선_바디_생성("green", "10", "2호선", "2", "1");
        지하철_노선_생성_요청(body);
    }
}
