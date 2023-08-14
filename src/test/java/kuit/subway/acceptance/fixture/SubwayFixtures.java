package kuit.subway.acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import kuit.subway.dto.station.request.StationCreateRequest;

public class SubwayFixtures {

    public static void 지하철역_생성(String name) {
        StationCreateRequest requestDto = 지하철역_생성_요청(name);
        RestAssured
                .given().log().all().body(requestDto)
                .contentType(ContentType.JSON)
                .when().post("/stations")
                .then().log().all();
    }

    public static StationCreateRequest 지하철역_생성_요청(String name) {
        return StationCreateRequest.builder()
                .name(name)
                .build();
    }
}
