package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.acceptance.fixtures.AuthAcceptanceFixtures.깃허브_로그인;
import static kuit.subway.acceptance.fixtures.AuthAcceptanceFixtures.자체_로그인;
import static kuit.subway.acceptance.fixtures.MemberAcceptanceFixtures.회원가입;
import static kuit.subway.utils.fixtures.AuthFixtures.깃허브유저;
import static kuit.subway.utils.fixtures.AuthFixtures.로그인_요청;
import static kuit.subway.utils.fixtures.MemberFixtures.회원가입_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {

    }

    @DisplayName("로그인을 진행한다.")
    @Test
    void login() {
        //given
        회원가입(회원가입_요청("test@test.com", "12345678!", 10));

        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test@test.com", "12345678!"));

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo("test@test.com")
        );
    }

    @DisplayName("잘못된 이메일로 로그인시, 예외가 발생한다.")
    @Test
    void login_Throw_Exception_If_Invalid_Email() {
        //given
        회원가입(회원가입_요청("test@test.com", "12345678!", 10));

        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test1@test.com", "12345678!"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("잘못된 비밀번호로 로그인시, 예외가 발생한다.")
    @Test
    void login_Throw_Exception_If_Invalid_Password() {
        //given
        회원가입(회원가입_요청("test@test.com", "12345678!", 10));

        //when
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청("test@test.com", "12345678!!"));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Github 로그인을 진행한다.")
    @Test
    void githubLogin(){
        //when
        ExtractableResponse<Response> response = 깃허브_로그인(깃허브유저.getCode());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(깃허브유저.getEmail())
        );
    }
}
