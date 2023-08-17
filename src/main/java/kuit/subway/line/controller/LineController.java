package kuit.subway.line.controller;

import jakarta.validation.Valid;
import kuit.subway.line.dto.request.LineRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LineController {

    private final LineService lineService;

    @PostMapping("/lines")
    public ResponseEntity<LineCreateResponse> createLine(@Valid @RequestBody LineRequest request) {
        LineCreateResponse response = lineService.createLine(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/lines/{lineId}")
    public ResponseEntity<LineResponse> showLine(@PathVariable Long lineId) {
        LineResponse response = lineService.showLine(lineId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/lines/{lineId}")
    public ResponseEntity<LineResponse> updateLine(@PathVariable Long lineId,
                                                   @Valid @RequestBody LineRequest request) {
        LineResponse response = lineService.updateLine(lineId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/lines/{lineId}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long lineId) {
        lineService.deleteLine(lineId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
