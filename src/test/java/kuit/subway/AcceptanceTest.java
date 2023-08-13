package kuit.subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.utils.DatabaseCleanup;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        // 개발 환경 포트와 인수테스트 환경 포트 분리
        RestAssured.port = port;
        databaseCleanup.execute();
    }

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

        extract.statusCode();
        Assertions.assertEquals(200, extract.statusCode());
    }

    @Test
    void getStations() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "강남역");

        RestAssured.given().contentType("application/json")
                .body(jsonObj.toString())
                .post("/stations")
                .then().extract();

        jsonObj.put("name", "건대입구역");

        RestAssured.given().contentType("application/json")
                .body(jsonObj.toString())
                .post("/stations")
                .then().extract();

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
                .when().post("/stations")
                .then().log().all()
                .extract();


        ExtractableResponse<Response> extract =  RestAssured.given().log().all()
                .when().delete("/stations/1")
                .then().log().all()
                .extract();

        extract.statusCode();
        Assertions.assertEquals(204, extract.statusCode());
    }
}
