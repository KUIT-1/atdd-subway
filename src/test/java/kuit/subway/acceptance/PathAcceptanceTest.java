package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static kuit.subway.acceptance.fixtures.LineAcceptanceFixtures.*;
import static kuit.subway.acceptance.fixtures.PathAcceptanceFixtures.경로_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PathAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        구호선_생성();
        팔호선_생성();
        이호선_생성();
        부산선_생성();
    }
    
    @DisplayName("경로가 제대로 조회된다.")
    @Test
    void showPath(){
        //when
        ExtractableResponse<Response> response = 경로_조회(1L, 4L);
        List<Object> stations = response.jsonPath().getList("stations");
        Object distance = response.jsonPath().get("distance");

        //then
        assertAll(
                () -> assertThat(stations).hasSize(4)
                        .extracting("name")
                        .containsExactly("둔촌역", "군자역", "어대역", "건대입구역"),
                () -> assertThat(distance).isEqualTo(15),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("출발역과 도착역이 같은 경우 예외가 발생한다.")
    @Test
    void showPath_Throw_Exception_If_Duplicate_Source_And_Target(){
        //when
        ExtractableResponse<Response> response = 경로_조회(1L, 1L);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("출발역과 도착역이 연결되지 않는 경우 예외가 발생한다.")
    @Test
    void showPath_Throw_Exception_If_Not_Connect_Path(){
        //when
        ExtractableResponse<Response> response = 경로_조회(1L, 5L);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("존재하지 않은 출발역이나 도착역을 조회할 경우 예외가 발생한다.")
    @Test
    void showPath_Throw_Exception_If_Not_Existed_Station(){
        //when
        ExtractableResponse<Response> response = 경로_조회(10L, 11L);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
