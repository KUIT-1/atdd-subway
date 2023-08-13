package kuit.subway.controller;

import kuit.subway.dto.station.request.StationCreateRequestDto;
import kuit.subway.dto.station.response.StationCreateResponseDto;
import kuit.subway.dto.station.response.StationResponseDto;
import kuit.subway.service.StationService;
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
    public ResponseEntity<StationCreateResponseDto> createStation(@RequestBody StationCreateRequestDto requestDto) {
        StationCreateResponseDto responseDto = stationService.createStation(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
