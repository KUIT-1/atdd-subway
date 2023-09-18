package kuit.subway.auth.service.github;

import kuit.subway.auth.service.github.userinfo.GithubUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "github-userinfo-client", url = "https://api.github.com/user")
public interface GithubUserInfoClient {

    @GetMapping
    GithubUserInfo getUserInfo(@RequestHeader(name = "Authorization") String authorization);
}
