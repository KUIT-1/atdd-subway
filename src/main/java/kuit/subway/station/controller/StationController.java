package kuit.subway.station.controller;

import jakarta.validation.Valid;
import kuit.subway.station.dto.request.StationCreateRequest;
import kuit.subway.station.dto.response.StationCreateResponse;
import kuit.subway.station.dto.response.StationResponse;
import kuit.subway.station.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StationController {

    private final StationService stationService;

    @PostMapping("/stations")
    public ResponseEntity<StationCreateResponse> createStation(@Valid @RequestBody StationCreateRequest requestDto) {
        StationCreateResponse responseDto = stationService.createStation(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<StationResponse>> showStations() {
        List<StationResponse> responseDtos = stationService.showStations();
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/stations/{stationId}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long stationId) {
        stationService.deleteStation(stationId);
        return ResponseEntity.status(204).build();
    }
}
