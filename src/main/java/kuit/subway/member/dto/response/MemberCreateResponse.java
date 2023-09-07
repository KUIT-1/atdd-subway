package kuit.subway.member.dto.response;

import kuit.subway.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberCreateResponse {

    private Long id;

    public static MemberCreateResponse of(Member member){
        return MemberCreateResponse.builder()
                .id(member.getId())
                .build();
    }
}
