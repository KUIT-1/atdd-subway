package kuit.subway.utils.fixtures;

import kuit.subway.auth.dto.request.LoginRequest;
import kuit.subway.auth.service.github.userinfo.GithubUserInfo;
import kuit.subway.auth.service.github.userinfo.OAuthUserInfo;
import kuit.subway.global.exception.SubwayException;

import java.util.Arrays;

import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_MEMBER;

public enum AuthFixtures {

    깃허브유저("member_code", 깃허브_유저_생성());

    private String code;
    private OAuthUserInfo userInfo;

    AuthFixtures(final String code, final OAuthUserInfo userInfo) {
        this.code = code;
        this.userInfo = userInfo;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return userInfo.getEmail();
    }

    public static OAuthUserInfo parseOAuthUserInfo(final String code) {
        AuthFixtures AuthFixtures = Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_MEMBER));
        return AuthFixtures.userInfo;
    }

    public static LoginRequest 로그인_요청(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    private static GithubUserInfo 깃허브_유저_생성() {
        String 깃허브_이메일 = "test@github.com";
        return new GithubUserInfo(깃허브_이메일);
    }
}
