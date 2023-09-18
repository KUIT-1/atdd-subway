package kuit.subway.auth.service;

import kuit.subway.auth.service.github.userinfo.OAuthUserInfo;

public interface OAuthProvider {

    OAuthUserInfo getUserInfo(String code);
}
