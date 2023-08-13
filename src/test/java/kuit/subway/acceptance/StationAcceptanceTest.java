package kuit.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.station.request.StationCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationAcceptanceTest extends AcceptanceTest{

    @DisplayName("지하철 역을 생성한다.")
    @Test
    void createStationTest() {
        String path = "/stations";
        StationCreateRequestDto requestDto = StationCreateRequestDto.builder()
                .name("강남역")
                .build();

        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .statusCode(200); // statusCode 검증
    }
}
