package kuit.subway.dto.station.request;

import jakarta.validation.constraints.Size;
import kuit.subway.entity.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationCreateRequest {

    @Size(min = 2, max = 20, message = "역의 이름은 2-20글자 사이여야 합니다.")
    private String name;

    public Station toEntity() {
        return Station.builder()
                .name(name)
                .build();
    }
}
