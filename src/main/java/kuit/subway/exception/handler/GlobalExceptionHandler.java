package kuit.subway.exception.handler;

import kuit.subway.exception.ExceptionStatus;
import kuit.subway.exception.SubwayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SubwayException.class)
    public ResponseEntity<Object> handleSubwayException(SubwayException e) {
        ExceptionStatus status = e.getStatus();
        return ResponseEntity.status(status.getHttpStatus())
                .body(status.getMessage());
    }
}
