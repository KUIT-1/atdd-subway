package kuit.subway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus implements ExceptionStatus {

    // common exception
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 2000, "잘못된 요청이 존재합니다."),
    INVALID_URL(HttpStatus.NOT_FOUND, 4000, "잘못된 URL 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 내부 오류입니다."),

    // station exception
    DUPLICATED_STATION(HttpStatus.BAD_REQUEST, 1001, "중복된 이름의 역이 존재합니다."),
    NOT_EXISTED_STATION(HttpStatus.BAD_REQUEST, 1002, "존재하지 않는 역입니다."),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
