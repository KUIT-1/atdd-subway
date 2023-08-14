package kuit.subway.station;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kuit.subway.station.StationStep.PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StationTest extends AcceptanceTest {

    @Test
    void createStation() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(PATH)
                .then().log().all()
                .extract();

        assertEquals(1L, response.jsonPath().getLong("id"));
        assertEquals(201, response.statusCode());
    }

    }

    @Test
    void getStations() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(PATH)
                .then().log().all();

        body.put("name", "성수역");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/stations")
                .then().log().all();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/stations")
                .then().log().all()
                .extract();


        // then
        assertEquals(200, response.statusCode());

        List<String> stationNames = response.jsonPath().getList("name");
        List<Integer> stationId = response.jsonPath().getList("id");

        assertEquals(1, stationId.get(0));
        assertEquals("강남역", stationNames.get(0));

        assertEquals(2, stationId.get(1));
        assertEquals("성수역", stationNames.get(1));

    }

    @Test
    void deleteSubway() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/stations");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/stations/1")
                .then().log().all()
                .extract();

        // then
        assertEquals(204, response.statusCode());
    }
}