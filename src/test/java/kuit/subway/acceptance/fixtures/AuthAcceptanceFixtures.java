package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.auth.dto.request.LoginRequest;

import static kuit.subway.utils.RestAssuredUtils.post;

public class AuthAcceptanceFixtures {

    private static final String BASE_PATH = "/login";

    public static ExtractableResponse<Response> 자체_로그인(LoginRequest request) {
        return post(request, BASE_PATH);
    }
}
