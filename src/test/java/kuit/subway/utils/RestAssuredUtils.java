package kuit.subway.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.auth.dto.response.TokenResponse;

public class RestAssuredUtils {

    public static ExtractableResponse<Response> post(Object requestBody, String path) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> post(Object requestBody, String path, Object... pathParams) {
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

    public static ExtractableResponse<Response> getMyInfo(String path, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getGithubLogin(String path, String code) {
        return RestAssured
                .given().log().all()
                .queryParams("code", code)
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static String getGithubLoginToken(String path, String code) {
        return RestAssured
                .given().log().all()
                .queryParams("code", code)
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }

    public static ExtractableResponse<Response> get(String path, Object... pathParams) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path, pathParams)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getPath(String path, Long sourceStationId, Long targetStationId) {
        return RestAssured
                .given().log().all()
                .queryParams("sourceStationId", sourceStationId)
                .queryParams("targetStationId", targetStationId)
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(Object requestBody, String path) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().patch(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(Object requestBody, String path, Object... pathParams) {
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

    public static ExtractableResponse<Response> deleteStationInSection(String path, Long stationId, Object... pathParams) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParams("stationId", stationId)
                .when().delete(path, pathParams)
                .then().log().all()
                .extract();
    }
}
