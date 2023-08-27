package kuit.subway.line.controller;

import jakarta.validation.Valid;
import kuit.subway.line.dto.request.LineRequest;
import kuit.subway.line.dto.request.LineUpdateRequest;
import kuit.subway.line.dto.request.SectionRequest;
import kuit.subway.line.dto.response.LineCreateResponse;
import kuit.subway.line.dto.response.LineResponse;
import kuit.subway.line.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/lines")
@RequiredArgsConstructor
@Slf4j
public class LineController {

    private final LineService lineService;

    @PostMapping
    public ResponseEntity<LineCreateResponse> createLine(@Valid @RequestBody LineRequest request) {
        LineCreateResponse response = lineService.createLine(request);
        return ResponseEntity.created(URI.create("/stations/" + response.getId()))
                .body(response);
    }

    @GetMapping("/{lineId}")
    public ResponseEntity<LineResponse> showLine(@PathVariable Long lineId) {
        LineResponse response = lineService.showLine(lineId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{lineId}")
    public ResponseEntity<LineResponse> updateLine(@PathVariable Long lineId,
                                                   @Valid @RequestBody LineUpdateRequest request) {
        LineResponse response = lineService.updateLine(lineId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{lineId}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long lineId) {
        lineService.deleteLine(lineId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lineId}/sections")
    public ResponseEntity<LineResponse> createSection(@PathVariable Long lineId,
                                                      @Valid @RequestBody SectionRequest request) {
        LineResponse response = lineService.createSection(lineId, request);
        return ResponseEntity.created(URI.create("/line/" + response.getId() + "/sections"))
                .body(response);
    }

    @DeleteMapping("/{lineId}/sections/{stationId}")
    public ResponseEntity<LineResponse> deleteSection(@PathVariable Long lineId,
                                                      @PathVariable Long stationId) {
        LineResponse response = lineService.deleteSection(lineId, stationId);
        return ResponseEntity.ok(response);
    }
}
