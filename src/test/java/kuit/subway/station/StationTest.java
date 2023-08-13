package kuit.subway.station;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StationTest extends AcceptanceTest {
    @Test
    void createStation() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "강남역");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when().post("/stations")
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());
    }

    @Test
    void getStations() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "강남역");

        RestAssured.given().contentType("application/json")
                .body(jsonObj.toString())
                .post("/stations");

        jsonObj.put("name", "건대입구역");

        RestAssured.given().contentType("application/json")
                .body(jsonObj.toString())
                .post("/stations");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when().get("/stations")
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());

        List<String> stationNames = extract.jsonPath().getList("name");
        List<Integer> stationId = extract.jsonPath().getList("id");

        Assertions.assertEquals(1, stationId.get(0));
        Assertions.assertEquals("강남역", stationNames.get(0));

        Assertions.assertEquals(2, stationId.get(1));
        Assertions.assertEquals("건대입구역", stationNames.get(1));

    }

    @Test
    void deleteSubway() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "강남역");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(jsonObj.toString())
                .post("/stations");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when().delete("/stations/1")
                .then().log().all()
                .extract();

        extract.statusCode();
        Assertions.assertEquals(204, extract.statusCode());
    }
}