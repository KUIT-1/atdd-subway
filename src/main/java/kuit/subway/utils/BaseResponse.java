package kuit.subway.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import static kuit.subway.utils.BaseResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"responseCode", "responseMessage", "result"})
public class BaseResponse<T>{
    private final int responseCode;
    private final String responseMessage;
    private final T result;

    public BaseResponse(BaseResponseStatus status, T result) {
        this.responseCode = status.getResponseCode();
        this.responseMessage = status.getResponseMessage();
        this.result = result;
    }


    public BaseResponse(BaseResponseStatus status) {
        this.responseCode = status.getResponseCode();
        this.responseMessage = status.getResponseMessage();
        this.result = null;
    }

    public BaseResponse(T result) {
        this.responseCode = SUCCESS.getResponseCode();
        this.responseMessage = SUCCESS.getResponseMessage();
        this.result = result;
    }
}
