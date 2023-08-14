package kuit.subway.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SubwayException extends RuntimeException{

    private final ExceptionStatus status;
}
