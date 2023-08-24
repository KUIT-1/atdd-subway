package kuit.subway.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus{
    // 1000 : 요청 성공
    SUCCESS(HttpStatus.OK,1000, "요청에 성공하였습니다."),
    CREATED_SUCCESS(HttpStatus.CREATED,1001, "생성에 성공하였습니다."),
    DELETED_SUCCESS(HttpStatus.NO_CONTENT,1002, "삭제에 성공하였습니다."),
    EMPTY_INFO(HttpStatus.OK, 1003, "아무런 정보가 존재하지 않습니다."),
    REGISTERED_SUCCESS(HttpStatus.OK,1004, "등록에 성공하였습니다."),

    // 2000 : STATION EXCEPTION
    DUPLICATED_STATION(HttpStatus.CONFLICT,2000, "이미 존재하는 역입니다."),
    NONE_STATION(HttpStatus.BAD_REQUEST,2001, "존재하지 않는 역입니다."),
    NONE_STATION(HttpStatus.NOT_FOUND,2001, "존재하지 않는 역입니다."),

    // 2100 : Line Exception
    DUPLICATED_LINE(HttpStatus.CONFLICT,2100, "이미 존재하는 노선입니다."),
    NONE_LINE(HttpStatus.BAD_REQUEST, 2101, "존재하지 않는 노선입니다.");
    NONE_LINE(HttpStatus.NOT_FOUND, 2101, "존재하지 않는 노선입니다."),
    ALREADY_REGISTERED_STATION(HttpStatus.BAD_REQUEST, 2103, "해당 역이 이미 해당 노선에 등록되어있습니다."),
    ONLY_LAST_DOWNSTATION_REGISTER_ALLOWED(HttpStatus.BAD_REQUEST, 2104, "추가하고 싶은 구간의 상행역은 하행종점역이어야 합니다."),
    private final HttpStatus httpStatus;
    private final int responseCode;
    private final String responseMessage;
}