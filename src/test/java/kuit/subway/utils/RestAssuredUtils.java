package kuit.subway.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestAssuredUtils {

    public static ExtractableResponse<Response> post(String path, Object requestBody) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> post(String path, Object requestBody, Object... pathParams) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().post(path, pathParams)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> get(String path) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> get(String path, Object... pathParams) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path, pathParams)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(String path, Object requestBody) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().patch(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(String path, Object requestBody, Object... pathParams) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().patch(path, pathParams)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path, Object... pathParams) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(path, pathParams)
                .then().log().all()
                .extract();
    }
}
