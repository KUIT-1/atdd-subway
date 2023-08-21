package kuit.subway.utils.exception;

import kuit.subway.utils.BaseResponseStatus;
import lombok.Getter;

@Getter
public class LineException extends DomainException {
    public LineException(BaseResponseStatus status) {
        super(status);
    }
}
