package kuit.subway.auth.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthTokenRequest {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
