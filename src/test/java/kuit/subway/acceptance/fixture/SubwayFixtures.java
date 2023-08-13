package kuit.subway.acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.dto.station.request.StationCreateRequestDto;

public class SubwayFixtures {

    public static void createStation(String name) {
        StationCreateRequestDto requestDto = createStationRequestDto(name);
        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post("/stations")
                .then().log().all();
    }

    public static StationCreateRequestDto createStationRequestDto(String name) {
        return StationCreateRequestDto.builder()
                .name(name)
                .build();
    }
}
