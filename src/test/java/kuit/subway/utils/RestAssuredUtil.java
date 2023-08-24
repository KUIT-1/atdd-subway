package kuit.subway.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

public class RestAssuredUtil {

    public static ExtractableResponse<Response> post요청(String path, Object body){
        return RestAssured.given().log().all().contentType(ContentType.JSON).body(body)
                .when().post(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> post요청(String path, Map<String, String> pathParam, Object body){
        return RestAssured.given().log().all().contentType(ContentType.JSON).body(body)
                .when().post(path, pathParam)
                .then().log().all().extract();
    }
  
    public static ExtractableResponse<Response> get요청(String path){
        return RestAssured.given().log().all()
                .when().get(path)
                .then().log().all().extract();
    }
  
    public static ExtractableResponse<Response> get요청(String path, Map<String, String> pathParam){
        return RestAssured.given().log().all()
                .when().get(path, pathParam)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> delete요청(String path, Map<String, String> pathParam, Object body){
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(body)
                .when().delete(path, pathParam)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> delete요청(String path, Map<String, String> pathParam){
        return RestAssured.given().log().all()
                .when().delete(path, pathParam)
                .then().log().all().extract();
    }

}
