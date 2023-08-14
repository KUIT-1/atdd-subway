package kuit.subway.acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.dto.station.request.StationCreateRequest;

public class SubwayFixtures {

    public static void createStation(String name) {
        StationCreateRequest requestDto = createStationRequestDto(name);
        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post("/stations")
                .then().log().all();
    }

    public static StationCreateRequest createStationRequestDto(String name) {
        return StationCreateRequest.builder()
                .name(name)
                .build();
    }
}
