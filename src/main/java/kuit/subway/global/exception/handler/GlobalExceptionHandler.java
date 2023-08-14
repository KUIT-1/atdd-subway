package kuit.subway.global.exception.handler;

import kuit.subway.global.exception.ExceptionStatus;
import kuit.subway.global.exception.SubwayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit.subway.global.exception.CustomExceptionStatus.INVALID_PARAMETER;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SubwayException.class)
    public ResponseEntity<Object> handleSubwayException(SubwayException e) {
        ExceptionStatus status = e.getStatus();
        return ResponseEntity.status(status.getHttpStatus())
                .body(status.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgumentException() {
        ExceptionStatus status = INVALID_PARAMETER;
        return ResponseEntity.status(status.getHttpStatus())
                .body(status.getMessage());
    }
}
