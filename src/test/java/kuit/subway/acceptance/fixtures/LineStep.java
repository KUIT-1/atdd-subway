package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.acceptance.fixtures.StationStep.지하철_역_생성_요청;
import static kuit.subway.utils.RestAssuredUtil.*;
import static kuit.subway.acceptance.fixtures.LineFixture.*;

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
        return get요청(PATH + "/{id}", pathParam);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정_요청(String id, Object body){
        makePathParamById(id);
        return post요청(PATH + "/{id}", pathParam, body);
    }

    public static ExtractableResponse<Response> 지하철_노선_삭제_요청(String id){
        makePathParamById(id);
        return delete요청(PATH + "/{id}", pathParam);
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

    public static ExtractableResponse<Response> 지하철_2호선_생성_요청(String upStationName, String downStationName){
        return 지하철_노선_생성_Fixture(GREEN, TEN, 이호선이름, upStationName, downStationName);
    }

    public static ExtractableResponse<Response> 지하철_7호선_생성_요청(String upStationName, String downStationName){
        return 지하철_노선_생성_Fixture(DARKGREEN, TEN, 칠호선이름, upStationName, downStationName);
    }

    private static ExtractableResponse<Response> 지하철_노선_생성_Fixture(String color, String distance, String lineName, String upStationName, String downStationName){
        Long upId = 지하철_역_생성_요청(upStationName).jsonPath().getLong(ID_PATH);
        Long downId = 지하철_역_생성_요청(downStationName).jsonPath().getLong(ID_PATH);
        Map<String, String> body = 지하철_노선_바디_생성(color, distance, lineName, Long.toString(downId), Long.toString(upId));
        return 지하철_노선_생성_요청(body);
    }

}
