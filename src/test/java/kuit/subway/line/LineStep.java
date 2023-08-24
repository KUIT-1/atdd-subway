package kuit.subway.line;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.utils.RestAssuredUtil.*;

public class LineStep {
    public static final String PATH = "/lines";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String DISTANCE = "distance";
    public static final String DOWNSTATIONID = "downStationId";
    public static final String UPSTATIONID = "upStationId";

    public static Map<String, String> pathParam = new HashMap<>();

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(Object body) {
        return post요청(PATH, body);
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(String id){
        makePathParamById(id);
        return get요청(LineStep.PATH + "/{id}", pathParam);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정_요청(String id, Object body){
        makePathParamById(id);
        return post요청(LineStep.PATH + "/{id}", pathParam, body);
    }

    public static ExtractableResponse<Response> 지하철_노선_삭제_요청(String id){
        makePathParamById(id);
        return delete요청(LineStep.PATH + "/{id}", pathParam);
    }

    public static Map<String, String> 지하철_노선_바디_생성
            (String color, String distance, String name, String downStationId, String upStationId){
        Map<String, String> body =  new HashMap<>();
        body.put(COLOR, color);
        body.put(DISTANCE, distance);
        body.put(NAME, name);
        body.put(DOWNSTATIONID, downStationId);
        body.put(UPSTATIONID, upStationId);
        return body;
    }

    private static void makePathParamById(String id) {
        if(!pathParam.isEmpty())
            pathParam.clear();
        pathParam.put("id", id);
    }

}
