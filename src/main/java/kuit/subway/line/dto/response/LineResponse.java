package kuit.subway.line.dto.response;

import kuit.subway.line.domain.Line;
import kuit.subway.station.dto.response.StationResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static LineResponse of(Line line) {
        return LineResponse.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(line.getStations().stream().map(StationResponse::of).toList())
                .createdDate(line.getCreatedDate())
                .modifiedDate(line.getModifiedDate())
                .build();
    }
}
