package kuit.subway.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class MemberCreateRequest {

    @NotBlank(message = "이메일 입력해주세요.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+])[A-Za-z0-9~!@#$%^&*()_+]{8,}", message = "특수문자 포함 8자 이상 입력해야 합니다.")
    private String password;

    @Positive
    private Integer age;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .age(age)
                .build();
    }
}
