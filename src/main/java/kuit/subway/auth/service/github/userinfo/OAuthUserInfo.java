package kuit.subway.auth.service.github.userinfo;

import kuit.subway.member.domain.Member;

public interface OAuthUserInfo {

    String getEmail();

    Member toMember();
}
