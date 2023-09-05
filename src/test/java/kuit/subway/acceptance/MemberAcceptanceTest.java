package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.acceptance.fixtures.MemberAcceptanceFixtures.*;
import static kuit.subway.utils.fixtures.MemberFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp(){
        //when
        ExtractableResponse<Response> response = 회원가입(회원가입_요청("test@test.com", "12345678!", 10));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입시 검증조건을 만족하지 못하는 경우 예외가 발생한다.")
    @Test
    void signUp_Throw_Exception_If_Invalid_Request(){
        //when
        ExtractableResponse<Response> response = 회원가입(회원가입_요청("test", "12345678", -1));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
