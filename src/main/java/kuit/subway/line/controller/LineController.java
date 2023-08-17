package kuit.subway.line.controller;

import jakarta.validation.Valid;
import kuit.subway.line.dto.request.LineCreateRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LineController {

    private final LineService lineService;

    @PostMapping("/lines")
    public ResponseEntity<LineCreateResponse> createLine(@Valid @RequestBody LineCreateRequest request) {
        LineCreateResponse response = lineService.createLine(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
