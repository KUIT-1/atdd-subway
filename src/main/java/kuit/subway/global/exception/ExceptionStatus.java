package kuit.subway.global.exception;

import org.springframework.http.HttpStatus;


public interface ExceptionStatus {

    HttpStatus getHttpStatus();

    String getMessage();
}
