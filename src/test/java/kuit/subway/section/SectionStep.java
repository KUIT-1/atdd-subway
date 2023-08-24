package kuit.subway.section;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.line.LineStep;
import kuit.subway.utils.RestAssuredUtil;

import java.util.HashMap;
import java.util.Map;

public class SectionStep {
    public static final String PATH = LineStep.PATH + "/{id}/sections";

    public static Map<String, String> pathParam = new HashMap<>();

    public static ExtractableResponse<Response> 지하철_구간_등록_요청(String line_id, Object body){
        makePathParamById(line_id);
        return RestAssuredUtil.post요청(PATH, pathParam, body);
    }
    private static void makePathParamById(String id) {
        if(!pathParam.isEmpty())
            pathParam.clear();
        pathParam.put("id", id);
    }

}
