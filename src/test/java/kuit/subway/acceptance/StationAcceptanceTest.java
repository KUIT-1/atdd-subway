package kuit.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.station.request.StationCreateRequestDto;
import kuit.subway.fixture.SubwayFixtures;
import kuit.subway.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StationAcceptanceTest extends AcceptanceTest{

    private final StationRepository stationRepository;

    @Autowired
    public StationAcceptanceTest(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @DisplayName("지하철 역을 생성한다.")
    @Test
    void createStationTest() {
        String path = "/stations";
        StationCreateRequestDto requestDto = SubwayFixtures.createStationRequestDto("강남역");

        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("지하철역 목록을 조회한다.")
    @Test
    void showStationsTest(){
        String path = "/stations";
        stationRepository.save(SubwayFixtures.createStation("강남역"));
        stationRepository.save(SubwayFixtures.createStation("성수역"));

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("지하철역 하나를 삭제한다.")
    @Test
    void deleteStationTest(){
        String path = "/stations/{stationId}";
        stationRepository.save(SubwayFixtures.createStation("강남역"));

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(path, 1L)
                .then().log().all()
                .statusCode(204);
    }
}
