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
    public static final StationInfo 성수역INFO = new StationInfo(1L, 성수역);
    public static final StationInfo 강남역INFO = new StationInfo(2L, 강남역);
    public static final StationInfo 건대역INFO = new StationInfo(3L, 건대역);
    public static final StationInfo 교대역INFO = new StationInfo(4L, 교대역);
    public static final StationInfo 뚝섬역INFO = new StationInfo(5L, 뚝섬역);

    private record StationInfo(Long id, String name) {}

    public static Station create_역(StationInfo info){
        return new Station(info.id(), info.name());
    }

}
