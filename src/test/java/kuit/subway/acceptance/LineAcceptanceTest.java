package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static kuit.subway.acceptance.fixtures.LineAcceptanceFixtures.노선_생성;
import static kuit.subway.acceptance.fixtures.LineAcceptanceFixtures.노선_조회;
import static kuit.subway.acceptance.fixtures.StationAcceptanceFixtures.지하철역_생성;
import static kuit.subway.utils.fixtures.LineFixtures.노선_요청;
import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));

        //when
        ExtractableResponse<Response> response =
                노선_생성(노선_요청("경춘선", "grean", 10L, 2L, 1L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("검증조건을 만족시키지 않는 노선 생성 요청의 경우, 예외를 발생한다.")
    @Test
    void createLine_Throw_Exception_If_Invalid_Request(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));

        //when
        ExtractableResponse<Response> response =
                노선_생성(노선_요청("A".repeat(11),"G".repeat(11) , -1L, 2L, 1L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상행종점역과 하행종점역이 같은 노선 생성 요청의 경우, 예외를 발생한다.")
    @Test
    void createLine_Throw_Exception_If_Duplicated_Stations_Request(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));

        //when
        ExtractableResponse<Response> response =
                노선_생성(노선_요청("경춘선","grean" , 10L, 1L, 1L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void showLines(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));
        노선_생성(노선_요청("경춘선", "green", 10L, 1L, 2L));

        //when
        ExtractableResponse<Response> response = 노선_조회(1L);
        List<Object> stations = response.jsonPath().getList("stations.");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(stations).hasSize(2)
        );
    }

    @DisplayName("존재하지 않는 지하철 노선을 조회시, 예외를 발생한다.")
    @Test
    void showLines_Throw_Exception_If_Not_Existed_Line(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));
        노선_생성(노선_요청("경춘선", "green", 10L, 1L, 2L));

        //when
        ExtractableResponse<Response> response = 노선_조회(2L);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
