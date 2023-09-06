package kuit.subway.member.service;

import kuit.subway.global.exception.SubwayException;
import kuit.subway.member.domain.Member;
import kuit.subway.member.dto.request.MemberCreateRequest;
import kuit.subway.member.dto.response.MemberCreateResponse;
import kuit.subway.member.dto.response.MemberResponse;
import kuit.subway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuit.subway.global.exception.CustomExceptionStatus.DUPLICATED_EMAIL;
import static kuit.subway.global.exception.CustomExceptionStatus.NOT_EXISTED_MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberCreateResponse create(MemberCreateRequest request) {
        validateDuplicatedEmail(request.getEmail());
        Member member = memberRepository.save(request.toEntity());
        return MemberCreateResponse.of(member);
    }

    public MemberResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new SubwayException(NOT_EXISTED_MEMBER));
        return MemberResponse.of(member);
    }

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new SubwayException(DUPLICATED_EMAIL);
        }
    }
}
