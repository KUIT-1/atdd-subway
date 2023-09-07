package kuit.subway.utils.fixtures;

import kuit.subway.auth.dto.request.LoginRequest;

public class AuthFixtures {

    public static LoginRequest 로그인_요청(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
