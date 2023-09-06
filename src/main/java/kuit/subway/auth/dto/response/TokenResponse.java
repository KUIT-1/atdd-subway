package kuit.subway.auth.dto.response;

import kuit.subway.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponse {

    private Long memberId;
    private String accessToken;

    public static TokenResponse of(Member member, String accessToken) {
        return TokenResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .build();
    }
}
