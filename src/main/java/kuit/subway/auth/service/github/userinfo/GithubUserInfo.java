package kuit.subway.auth.service.github;

import kuit.subway.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubUserInfo {

    private String email;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .build();
    }
}
