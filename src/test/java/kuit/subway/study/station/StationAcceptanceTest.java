package kuit.subway.study.station;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.CreateStationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class StationAcceptanceTest extends AcceptanceTest {


    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(String url, Map params) {
        return post(url, params);
    }


    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {

        // given
        Map<String, String> station = new HashMap<>();
        station.put("name", "강남역");

        // when
        ExtractableResponse<Response> res = 지하철_노선_생성_요청(STATION_PATH, station);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("지하철역 목록 조회 인수 테스트")

    @Test
    void getAllStations() {

        // given
        Map<String, String> station1 = new HashMap<>();
        station1.put("name", "강남역");

        Map<String, String> station2 = new HashMap<>();
        station1.put("name", "성수역");

        지하철_노선_생성_요청(STATION_PATH, station1);
        지하철_노선_생성_요청(STATION_PATH, station2);

        // when
        ExtractableResponse<Response> res = get(STATION_PATH);

        // then
        assertEquals(2, res.jsonPath().getList("").size());
    }

    @DisplayName("지하철역 삭제 인수 테스트")
    @Test
    void deleteStation() {

        // given
        Map<String, String> station = new HashMap<>();
        station.put("name", "강남역");

        ExtractableResponse<Response> res = 지하철_노선_생성_요청(STATION_PATH, station);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> deleteResponse = delete(STATION_PATH + id);

        // then
        assertEquals(200, deleteResponse.statusCode());

    }

}