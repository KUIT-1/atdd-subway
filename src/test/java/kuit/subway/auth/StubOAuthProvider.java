package kuit.subway.auth;

import kuit.subway.auth.service.OAuthProvider;
import kuit.subway.auth.service.github.userinfo.OAuthUserInfo;
import kuit.subway.utils.fixtures.AuthFixtures;

public class StubOAuthProvider implements OAuthProvider {

    @Override
    public OAuthUserInfo getUserInfo(String code) {
        return AuthFixtures.parseOAuthUserInfo(code);
    }
}
