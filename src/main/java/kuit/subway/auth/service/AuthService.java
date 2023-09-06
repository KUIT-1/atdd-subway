package kuit.subway.auth.service;

import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.auth.dto.request.LoginRequest;
import kuit.subway.auth.dto.response.TokenResponse;
import kuit.subway.global.exception.SubwayException;
import kuit.subway.member.domain.Member;
import kuit.subway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuit.subway.global.exception.CustomExceptionStatus.INVALID_PASSWORD;
import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_MEMBER));
        validatePassword(member, request.getPassword());
        String accessToken = jwtTokenProvider.createToken(member.getId());
        return TokenResponse.of(member, accessToken);
    }

    private void validatePassword(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new SubwayException(INVALID_PASSWORD);
        }
    }
}
