package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.acceptance.fixtures.LineAcceptanceFixtures.노선_생성;
import static kuit.subway.acceptance.fixtures.StationAcceptanceFixtures.지하철역_생성;
import static kuit.subway.utils.fixtures.LineFixtures.노선_생성_요청;
import static kuit.subway.utils.fixtures.StationFixtures.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine(){
        //given
        지하철역_생성(지하철역_생성_요청("강남역"));
        지하철역_생성(지하철역_생성_요청("성수역"));

        //when
        ExtractableResponse<Response> response =
                노선_생성(노선_생성_요청("경춘선", "grean", 10L, 2L, 1L));

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
                노선_생성(노선_생성_요청("A".repeat(11),"G".repeat(11) , -1L, 2L, 1L));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
