package kuit.subway.auth.service.github.userinfo;

import kuit.subway.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubUserInfo implements OAuthUserInfo{

    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Member toMember() {
        return Member.builder()
                .email(email)
                .build();
    }
}
