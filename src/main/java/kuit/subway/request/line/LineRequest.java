package kuit.subway.request.line;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineRequest {
    @Pattern(regexp = "[a-zA-Z]+", message = "영문")
    String color;

    @Positive
    Long distance;

    @Pattern(regexp = "[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣1-9]+", message = "영문, 한글, 숫자만 가능")
    @Length(min = 1, max = 20, message = "1 ~ 20자리 이내")
    String name;

    @Positive
    Long downStationId;

    @Positive
    Long upStationId;

}
