package kuit.subway.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus implements ExceptionStatus {

    // common exception
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "검증조건을 만족하지 못하는 요청이 들어왔습니다."),
    TYPE_MISS_MATCH(HttpStatus.BAD_REQUEST, "잘못된 타입으로 요청이 들어왔습니다."),
    INVALID_URL(HttpStatus.NOT_FOUND, "잘못된 URL 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    // station exception
    DUPLICATED_STATION(HttpStatus.BAD_REQUEST, "중복된 이름의 역이 존재합니다."),
    NOT_EXISTED_STATION(HttpStatus.BAD_REQUEST, "존재하지 않는 역입니다."),

    // line exception
    NOT_EXISTED_LINE(HttpStatus.BAD_REQUEST, "존재하지 않는 노선입니다."),
    DUPLICATED_UP_STATION_AND_DOWN_STATION(HttpStatus.BAD_REQUEST, "상행종점역과 하행종점역이 중복됩니다."),

    // section exception
    EXISTED_STATION_IN_SECTIONS(HttpStatus.BAD_REQUEST, "상행역과 하행역이 이미 노선에 등록되어 있습니다."),
    EXCEED_DISTANCE(HttpStatus.BAD_REQUEST, "추가되는 역 사이의 거리는, 기존 역 사이 길이보다 크거나 같을 수 없습니다."),
    UNDER_MINIMUM_SECTION_SIZE(HttpStatus.BAD_REQUEST, "상행 종점역과 하행 종점역만 있는 경우, 구간을 삭제할 수 없습니다."),
    NOT_EXISTED_STATION_IN_SECTION(HttpStatus.NOT_FOUND, "노선에 등록되어 있지 않은 역입니다."),

    // path exception
    INVALID_GRAPH(HttpStatus.BAD_REQUEST, "해당 역이 그래프에 포함되어 있지 않습니다."),
    NOT_EXISTED_PATH(HttpStatus.BAD_REQUEST, "출발역과 도착역이 연결되어있지 않습니다."),
    DUPLICATED_PATH_REQUEST(HttpStatus.BAD_REQUEST, "출발역과 도착역이 같을 수 없습니다."),

    // member exception
    NOT_EXISTED_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATED_EMAIL(HttpStatus.NOT_FOUND, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),

    // jwt exception
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 AccessToken 입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 AccessToken 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
