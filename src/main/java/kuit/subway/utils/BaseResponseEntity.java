package kuit.subway.utils;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import static kuit.subway.utils.BaseResponseStatus.SUCCESS;

@Getter
public class BaseResponseEntity<T> extends ResponseEntity<BaseResponse<T>> {

    public BaseResponseEntity(BaseResponseStatus status, T result) {
        super(new BaseResponse<T>(status, result), status.getHttpStatus());
    }

    public BaseResponseEntity(BaseResponseStatus status) {
        super(new BaseResponse<T>(status), status.getHttpStatus());
    }

    public BaseResponseEntity(T result) {
        super(new BaseResponse<T>(result), SUCCESS.getHttpStatus());
    }

}
