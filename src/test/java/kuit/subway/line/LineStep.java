package kuit.subway.line;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.utils.RestAssuredUtil.post요청;

public class LineStep {
    public static final String PATH = "/lines";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String DISTANCE = "distance";
    public static final String DOWNSTATIONID = "downStationId";
    public static final String UPSTATIONID = "upStationId";


    public static ExtractableResponse<Response> 지하철_노선_생성_요청(Map<String, String> body) {
        return post요청(PATH, body);
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(String id){
        return RestAssured
                .given().log().all().pathParam("id", id)
                .when().get(LineStep.PATH + "/{id}")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선_수정_요청(String id, Map<String, String> body){
        return RestAssured
                .given().log().all()
                .pathParam("id", id)
                .contentType(ContentType.JSON).body(body)
                .when().post(LineStep.PATH + "/{id}")
                .then().log().all().extract();
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

}
