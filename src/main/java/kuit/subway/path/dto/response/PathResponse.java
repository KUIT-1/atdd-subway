package kuit.subway.path.dto.response;

import kuit.subway.line.domain.Sections;
import kuit.subway.station.dto.response.StationResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PathResponse {

    private List<StationResponse> stations;
    private Long distance;

    public static PathResponse of(Sections sections) {
        return PathResponse.builder()
                .stations(sections.getStations().stream().map(StationResponse::of).toList())
                .distance(sections.getTotalDistance())
                .build();
    }
}
