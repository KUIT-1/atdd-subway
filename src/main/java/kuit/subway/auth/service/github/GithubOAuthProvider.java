package kuit.subway.auth.service.github;

import kuit.subway.auth.dto.request.OAuthTokenRequest;
import kuit.subway.auth.dto.response.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubOAuthProvider {

    private static final String TOKEN_TYPE = "Bearer ";

    private final GithubTokenClient githubTokenClient;
    private final GithubUserInfoClient githubUserInfoClient;
    private final String githubClientId;
    private final String githubClientSecret;
    private final String redirectUri;

    public GithubOAuthProvider(final GithubTokenClient githubTokenClient,
                               final GithubUserInfoClient githubUserInfoClient,
                               @Value("${oauth.github.client-id}") final String githubClientId,
                               @Value("${oauth.github.client-secret}") final String githubClientSecret,
                               @Value("${oauth.github.redirect-uri}") final String redirectUri) {
        this.githubTokenClient = githubTokenClient;
        this.githubUserInfoClient = githubUserInfoClient;
        this.githubClientId = githubClientId;
        this.githubClientSecret = githubClientSecret;
        this.redirectUri = redirectUri;
    }

    public GithubUserInfo getUserInfo(String code) {
        OAuthTokenRequest tokenRequest = new OAuthTokenRequest(githubClientId, githubClientSecret, code, redirectUri);
        OAuthTokenResponse tokenResponse = githubTokenClient.getAccessToken(tokenRequest);
        GithubUserInfo userInfo = githubUserInfoClient.getUserInfo(getAuthorizationFrom(tokenResponse));
        return userInfo;
    }

    private String getAuthorizationFrom(OAuthTokenResponse tokenResponse) {
        return TOKEN_TYPE + tokenResponse.getAccessToken();
    }
}
