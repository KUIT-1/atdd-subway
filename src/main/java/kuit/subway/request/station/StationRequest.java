package kuit.subway.request.station;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationRequest {
    @Pattern(regexp = "[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]+", message = "영문 또는 한글")
    @Length(min = 1, max = 20, message = "1 ~ 20자리 이내")
    private String name;
}
