package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.member.dto.request.MemberCreateRequest;

import static kuit.subway.utils.RestAssuredUtils.post;

public class MemberAcceptanceFixtures {

    private static final String BASE_PATH = "/members";

    public static ExtractableResponse<Response> 회원가입(MemberCreateRequest request) {
        return post(request, BASE_PATH);
    }
}
