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
}
