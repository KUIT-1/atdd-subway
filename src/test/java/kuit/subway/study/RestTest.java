package kuit.subway.study;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.acceptance.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class RestTest extends AcceptanceTest {

    @Test
    void restTest() {
        RestAssured.given().log().all()
                .when().get("/subway")
                .then().log().all()
                .extract();
    }

    @Test
    void createSubway() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .body(body)
                .when().get("/stations")
                .then().log().all()
                .extract();

        extract.statusCode();

        Assertions.assertEquals(200, extract.statusCode());
    }
}
