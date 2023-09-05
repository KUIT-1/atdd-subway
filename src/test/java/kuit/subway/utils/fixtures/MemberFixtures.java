package kuit.subway.utils.fixtures;

import kuit.subway.member.dto.request.MemberCreateRequest;

public class MemberFixtures {

    public static MemberCreateRequest 회원가입_요청(String email, String password, Integer age) {
        return MemberCreateRequest.builder()
                .email(email)
                .password(password)
                .age(age)
                .build();
    }
}
