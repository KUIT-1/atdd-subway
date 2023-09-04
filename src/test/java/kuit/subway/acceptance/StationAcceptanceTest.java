package kuit.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.acceptance.AcceptanceTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kuit.subway.acceptance.fixtures.StationStep.PATH;
import static kuit.subway.acceptance.fixtures.StationStep.지하철_역_생성_요청;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StationAcceptanceTest extends AcceptanceTest {
    private final String ID_PATH = "result.id";
    private final String NAME_PATH = "result.name";
  
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

        assertEquals(1L, response.jsonPath().getLong(ID_PATH));
        assertEquals(201, response.statusCode());
    }

    @Test
    void createStation_Using_StationStep(){
        ExtractableResponse<Response> response = 지하철_역_생성_요청("강남역");

        assertEquals(1L, response.jsonPath().getLong(ID_PATH));
        assertEquals(201, response.statusCode());
    }

    @Test
    void createStation_With_NullValue(){
        ExtractableResponse<Response> response = 지하철_역_생성_요청("");

        assertEquals(400, response.statusCode());
    }

    @Test
    void create_Duplicated_Station(){
        지하철_역_생성_요청("강남역");
        ExtractableResponse<Response> response = 지하철_역_생성_요청("강남역");

        assertEquals(409, response.statusCode());
    }


    @Test
    void getStations() {
        // given
        지하철_역_생성_요청("강남역");
        지하철_역_생성_요청("성수역");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/stations")
                .then().log().all()
                .extract();

        List<String> stationNames = response.jsonPath().getList(NAME_PATH);
        List<Long> stationIds = response.jsonPath().getList(ID_PATH)
                                        .stream().map(id -> Long.valueOf(id.toString()))
                                        .toList();

        assertEquals(200, response.statusCode());
        assertEquals(1L, stationIds.get(0));
        assertEquals("강남역", stationNames.get(0));
        assertEquals(2L, stationIds.get(1));
        assertEquals("성수역", stationNames.get(1));

    }

    @Test
    void deleteSubway() {
        // given
        지하철_역_생성_요청("강남역");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/stations/1")
                .then().log().all()
                .extract();

        // then
        assertEquals(204, response.statusCode());
    }
}