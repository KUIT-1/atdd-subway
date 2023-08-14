package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static kuit.subway.acceptance.fixtures.StationAcceptanceFixtures.*;
import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StationAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 역을 생성한다.")
    @Test
    void createStationTest() {
        //when
        ExtractableResponse<Response> response = 지하철역_생성(지하철역_생성_요청("강남역"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @DisplayName("지하철 역의 이름이 2-20글자가 아니라면 예외를 발생한다.")
    @Test
    void createStationTest_Throw_Exception_If_Invalid_Station_Name() {
        //when
        ExtractableResponse<Response> response = 지하철역_생성(지하철역_생성_요청("A".repeat(21)));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("지하철역 목록을 조회한다.")
    @Test
    void showStationsTest() {
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));

        //when
        ExtractableResponse<Response> response = 지하철역_조회();
        List<Object> results = response.jsonPath().getList(".");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(results).hasSize(2)
        );
    }

    @DisplayName("지하철역 하나를 삭제한다.")
    @Test
    void deleteStationTest() {
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));

        //when
        ExtractableResponse<Response> response = 지하철역_삭제();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
