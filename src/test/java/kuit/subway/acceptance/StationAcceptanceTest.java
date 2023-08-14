package kuit.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.acceptance.fixture.SubwayFixtures;
import kuit.subway.dto.station.request.StationCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationAcceptanceTest extends AcceptanceTest{

    @DisplayName("지하철 역을 생성한다.")
    @Test
    void createStationTest() {
        //given
        String path = "/stations";
        StationCreateRequest requestDto = SubwayFixtures.createStationRequestDto("강남역");

        //when & then
        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("지하철역 목록을 조회한다.")
    @Test
    void showStationsTest() {
        //given
        String path = "/stations";
        SubwayFixtures.createStation("강남역");
        SubwayFixtures.createStation("성수역");

        //when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("지하철역 하나를 삭제한다.")
    @Test
    void deleteStationTest() {
        //given
        String path = "/stations/{stationId}";
        SubwayFixtures.createStation("강남역");

        //when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(path, 1L)
                .then().log().all()
                .statusCode(204);
    }
}
