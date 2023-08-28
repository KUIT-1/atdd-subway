package kuit.subway.request.line;


import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateLineRequest {
    @Pattern(regexp = "[a-zA-Z]+", message = "영문")
    private String color;

    @Pattern(regexp = "[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣1-9]+", message = "영문, 한글, 숫자만 가능")
    @Length(min = 1, max = 20, message = "1 ~ 20자리 이내")
    private String name;
}
