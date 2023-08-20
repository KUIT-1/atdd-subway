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

    // 2000 : STATION EXCEPTION
    DUPLICATED_STATION(HttpStatus.CONFLICT,2000, "이미 존재하는 역입니다."),
    NONE_STATION(HttpStatus.BAD_REQUEST,2001, "존재하지 않는 역입니다.");
    NONE_STATION(HttpStatus.BAD_REQUEST,2001, "존재하지 않는 역입니다."),

    // 2100 : Line Exception
    DUPLICATED_LINE(HttpStatus.CONFLICT,2100, "이미 존재하는 노선입니다."),
    NONE_LINE(HttpStatus.BAD_REQUEST, 2101, "존재하지 않는 노선입니다.");
    private final HttpStatus httpStatus;
    private final int responseCode;
    private final String responseMessage;
}