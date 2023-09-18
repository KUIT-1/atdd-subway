package kuit.subway.auth.controller;

import jakarta.validation.Valid;
import kuit.subway.auth.dto.request.LoginRequest;
import kuit.subway.auth.dto.response.TokenResponse;
import kuit.subway.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/github")
    public ResponseEntity<TokenResponse> githubLogin(@RequestParam String code) {
        TokenResponse response = authService.githubLogin(code);
        return ResponseEntity.ok(response);
    }
}
