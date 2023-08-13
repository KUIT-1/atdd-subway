package kuit.subway.fixture;

import kuit.subway.dto.station.request.StationCreateRequestDto;
import kuit.subway.entity.Station;

public class SubwayFixtures {

    public static Station createStation(String name) {
        return Station.builder()
                .name(name)
                .build();
    }

    public static StationCreateRequestDto createStationRequestDto(String name) {
        return StationCreateRequestDto.builder()
                .name(name)
                .build();
    }
}
