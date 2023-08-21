package kuit.subway.utils.exception;

import kuit.subway.utils.BaseResponseStatus;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException{
    private final BaseResponseStatus status;

    public DomainException(BaseResponseStatus status) {
        super(status.getResponseMessage());
        this.status = status;
    }

}
