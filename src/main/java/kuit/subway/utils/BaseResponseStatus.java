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

    // 2000 : STATION EXCEPTION
    DUPLICATED_STATION(HttpStatus.CONFLICT,2000, "이미 존재하는 역입니다."),
    NONE_STATION(HttpStatus.BAD_REQUEST,2001, "존재하지 않는 역입니다.");

    private final HttpStatus httpStatus;
    private final int responseCode;
    private final String responseMessage;
}