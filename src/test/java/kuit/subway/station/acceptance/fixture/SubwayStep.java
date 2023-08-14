package kuit.subway.station.acceptance.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.utils.RestAssuredUtils;
import kuit.subway.station.dto.request.StationCreateRequest;

import static kuit.subway.station.acceptance.fixture.SubwayFixture.지하철역_생성_요청;

public class SubwayStep {

    public static ExtractableResponse<Response> 지하철역_생성(String name) {
        StationCreateRequest requestDto = 지하철역_생성_요청(name);
        return RestAssuredUtils.post("/stations", requestDto);
    }
}
