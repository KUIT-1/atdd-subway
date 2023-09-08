package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.auth.dto.request.LoginRequest;
import kuit.subway.auth.dto.response.TokenResponse;

import static kuit.subway.utils.RestAssuredUtils.*;
import static kuit.subway.utils.fixtures.AuthFixtures.로그인_요청;

public class AuthAcceptanceFixtures {

    private static final String BASE_PATH = "/login";

    public static ExtractableResponse<Response> 자체_로그인(LoginRequest request) {
        return post(request, BASE_PATH);
    }

    public static String 자체_로그인_토큰_발급(String email, String password) {
        ExtractableResponse<Response> response = 자체_로그인(로그인_요청(email, password));
        return response.as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 깃허브_로그인(String code) {
        return getGithubLogin(BASE_PATH+"/github", code);
    }

    public static String 깃허브_로그인_토큰_발급(String code) {
        return getGithubLoginToken(BASE_PATH+"/github", code);
    }
}
