package kuit.subway.path.controller;

import kuit.subway.path.dto.response.PathResponse;
import kuit.subway.path.service.PathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
@RequiredArgsConstructor
@Slf4j
public class PathController {

    private final PathService pathService;

    @GetMapping()
    public ResponseEntity<PathResponse> showPath(@RequestParam Long sourceStationId,
                                                 @RequestParam Long targetStationId) {
        PathResponse response = pathService.findPath(sourceStationId, targetStationId);
        return ResponseEntity.ok(response);
    }
}
