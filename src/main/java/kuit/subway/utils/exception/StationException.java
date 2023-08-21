package kuit.subway.utils.exception;

import kuit.subway.utils.BaseResponseStatus;
import lombok.Getter;

@Getter
public class StationException extends DomainException {
    public StationException(BaseResponseStatus status) {
        super(status);
    }
}
