package kuit.subway.member.dto.response;

import kuit.subway.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {

    private String email;
    private Integer age;

    public static MemberResponse of(Member member){
        return MemberResponse.builder()
                .email(member.getEmail())
                .age(member.getAge())
                .build();
    }
}
