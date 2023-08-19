package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static kuit.subway.acceptance.fixtures.LineAcceptanceFixtures.노선_생성;
import static kuit.subway.acceptance.fixtures.SectionAcceptanceFixtures.구간_생성;
import static kuit.subway.acceptance.fixtures.StationAcceptanceFixtures.지하철역_생성;
import static kuit.subway.utils.fixtures.LineFixtures.노선_요청;
import static kuit.subway.utils.fixtures.SectionFixtures.구간_생성_요청;
import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SectionAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));
        노선_생성(노선_요청("경춘선", "grean", 10L, 2L, 1L));
    }

    @DisplayName("노선에 구간을 등록한다.")
    @Test
    void createSection(){
        //given
        지하철역_생성(지하철역_생성_요청("건대입구역"));

        //when
        ExtractableResponse<Response> response = 구간_생성(1L, 구간_생성_요청(10L, 3L, 2L));
        List<Object> stations = response.jsonPath().getList("stations.");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(stations).hasSize(3)
        );
    }

    @DisplayName("새로운 구간의 상행역이 해당 노선에 등록되어있는 하행 종점역이 아닌 경우, 예외가 발생한다.")
    @Test
    void createSection_Throw_Exception_If_Mismatch_Up_Station_With_Existed_Down_Station(){
        //given
        지하철역_생성(지하철역_생성_요청("건대입구역"));

        //when
        ExtractableResponse<Response> response = 구간_생성(1L, 구간_생성_요청(10L, 3L, 1L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("새로운 구간의 하행역이 해당 노선에 등록되어 있는 경우, 예외를 발생한다.")
    @Test
    void createSection_Throw_Exception_If_Existed_Down_Station(){
        //given
        지하철역_생성(지하철역_생성_요청("건대입구역"));

        //when
        ExtractableResponse<Response> response = 구간_생성(1L, 구간_생성_요청(10L, 1L, 2L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
