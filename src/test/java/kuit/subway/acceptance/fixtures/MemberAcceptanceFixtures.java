package kuit.subway.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.member.dto.request.MemberCreateRequest;

import static kuit.subway.utils.RestAssuredUtils.getMyInfo;
import static kuit.subway.utils.RestAssuredUtils.post;

public class MemberAcceptanceFixtures {

    private static final String BASE_PATH = "/members";

    public static ExtractableResponse<Response> 회원가입(MemberCreateRequest request) {
        return post(request, BASE_PATH);
    }

    public static ExtractableResponse<Response> 내_정보_조회(String accessToken) {
        return getMyInfo(BASE_PATH + "/me", accessToken);
    }
}
