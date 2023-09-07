package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.acceptance.fixtures.AuthAcceptanceFixtures.자체_로그인;
import static kuit.subway.acceptance.fixtures.MemberAcceptanceFixtures.회원가입;
import static kuit.subway.utils.fixtures.AuthFixtures.로그인_요청;
import static kuit.subway.utils.fixtures.MemberFixtures.회원가입_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        회원가입(회원가입_요청("test@test.com", "12345678!", 10));
    }

    @DisplayName("로그인을 진행한다.")
    @Test
    void login() {
        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test@test.com", "12345678!"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("잘못된 이메일로 로그인시, 예외가 발생한다..")
    @Test
    void login_Throw_Exception_If_Invalid_Email() {
        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test1@test.com", "12345678!"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("잘못된 비밀번호로 로그인시, 예외가 발생한다..")
    @Test
    void login_Throw_Exception_If_Invalid_Password() {
        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test@test.com", "12345678!!"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
