package kuit.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.member.dto.request.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.acceptance.fixtures.AuthAcceptanceFixtures.깃허브_로그인_토큰_발급;
import static kuit.subway.acceptance.fixtures.AuthAcceptanceFixtures.자체_로그인_토큰_발급;
import static kuit.subway.acceptance.fixtures.MemberAcceptanceFixtures.내_정보_조회;
import static kuit.subway.acceptance.fixtures.MemberAcceptanceFixtures.회원가입;
import static kuit.subway.utils.fixtures.AuthFixtures.깃허브유저;
import static kuit.subway.utils.fixtures.MemberFixtures.회원가입_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @DisplayName("내 정보를 조회한다.")
    @Test
    void showMyInfo(){
        //given
        MemberCreateRequest signUpRequest = 회원가입_요청("test@test.com", "12345678!", 26);
        회원가입(signUpRequest);
        String accessToken = 자체_로그인_토큰_발급(signUpRequest.getEmail(), signUpRequest.getPassword());

        //when
        ExtractableResponse<Response> response = 내_정보_조회(accessToken);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(signUpRequest.getEmail()),
                () -> assertThat(response.jsonPath().getInt("age")).isEqualTo(signUpRequest.getAge())
        );
    }

    @DisplayName("Github 로그인을 통해 내 정보를 조회한다.")
    @Test
    void showMyInfo_With_Github_Login(){
        //given
        String accessToken = 깃허브_로그인_토큰_발급(깃허브유저.getCode());
        System.out.println("accessToken = " + accessToken);

        //when
        ExtractableResponse<Response> response = 내_정보_조회(accessToken);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(깃허브유저.getEmail())
        );

    }
}
