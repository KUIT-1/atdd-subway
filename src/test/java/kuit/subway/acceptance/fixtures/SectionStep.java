package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
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

    public static ExtractableResponse<Response> 지하철_구간_삭제_요청(String line_id, Object body){
        makePathParamById(line_id);
        return RestAssuredUtil.delete요청(PATH, pathParam, body);
    }

    private static void makePathParamById(String id) {
        if(!pathParam.isEmpty())
            pathParam.clear();
        pathParam.put("id", id);
    }

    public static Section create_구간(Station 하행역, Station 상행역, String 구간) {
        return Section.builder()
                .downStation(하행역)
                .upStation(상행역)
                .distance(Long.valueOf(구간))
                .build();
    }

}
