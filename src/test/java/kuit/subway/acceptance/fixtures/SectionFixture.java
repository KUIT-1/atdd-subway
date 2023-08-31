package kuit.subway.acceptance.fixtures;

import kuit.subway.domain.Section;
import kuit.subway.domain.Station;

public class SectionFixture {

    public static Section create_구간(Station 하행역, Station 상행역) {
        return Section.builder()
                .downStation(하행역)
                .upStation(상행역)
                .distance(Long.valueOf(LineFixture.TEN))
                .build();
    }
}
