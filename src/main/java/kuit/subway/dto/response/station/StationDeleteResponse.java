package kuit.subway.dto.response.station;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StationDeleteResponse {
    private String message;
    private Long id;
}