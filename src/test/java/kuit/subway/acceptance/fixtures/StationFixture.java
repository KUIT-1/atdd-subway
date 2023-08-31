package kuit.subway.acceptance.fixtures;

import kuit.subway.domain.Station;

public class StationFixture {

    public static final String 성수역 = "성수역";
    public static final String 강남역 = "강남역";
    public static final String 건대역 = "건대역";
    public static final String 교대역 = "교대역";
    public static final String 어린이대공원역 = "어린이대공원역";
    public static final String 뚝섬역 = "뚝섬역";


    // Mock...
    public static Station create_성수역(){
        return Station.builder()
                .id(1L)
                .name(성수역)
                .build();
    }

    public static Station create_강남역(){
        return Station.builder()
                .id(2L)
                .name(강남역)
                .build();
    }

    public static Station create_교대역(){
        return Station.builder()
                .id(3L)
                .name(교대역)
                .build();
    }

    public static Station create_뚝섬역() {
        return Station.builder()
                .id(4L)
                .name(뚝섬역)
                .build();
    }

    public static Station create_건대역() {
        return Station.builder()
                .id(5L)
                .name(건대역)
                .build();
    }

}
