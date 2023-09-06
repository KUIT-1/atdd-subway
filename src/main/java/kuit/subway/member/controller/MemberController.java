package kuit.subway.member.controller;

import jakarta.validation.Valid;
import kuit.subway.auth.LoginUserId;
import kuit.subway.member.dto.request.MemberCreateRequest;
import kuit.subway.member.dto.response.MemberCreateResponse;
import kuit.subway.member.dto.response.MemberResponse;
import kuit.subway.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberCreateResponse> signUp(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.create(request);
        return ResponseEntity.created(URI.create("/members" + response.getId()))
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMyInfo(@LoginUserId Long memberId) {
        MemberResponse response = memberService.getMyInfo(memberId);
        return ResponseEntity.ok(response);
    }
}
