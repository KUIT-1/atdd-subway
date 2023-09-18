package kuit.subway.auth.service.github;

import kuit.subway.auth.dto.request.OAuthTokenRequest;
import kuit.subway.auth.dto.response.OAuthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "github-token-client", url = "https://github.com/login/oauth/access_token")
public interface GithubTokenClient {

    @PostMapping(produces = "application/json")
    OAuthTokenResponse getAccessToken(@SpringQueryMap OAuthTokenRequest request);
}
