package kuit.subway.station;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.post;

public class StationStep {
    public static final String PATH = "/stations";
    public static final String NAME = "name";

    public static ExtractableResponse<Response> 지하철_역_생성_요청(String name) {
        Map<String, String> body = new HashMap<>();
        body.put(NAME, name);

        return RestAssured.given().log().all()
                .body(body).contentType(ContentType.JSON)
                .when().post(PATH)
                .then().log().all()
                .extract();
    }
}
