package kuit.subway.controller;

import kuit.subway.request.StationRequest;
import kuit.subway.response.CreateStationResponse;
import kuit.subway.response.ShowStationResponse;
import kuit.subway.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    @PostMapping
    public ResponseEntity<CreateStationResponse> createStation(
            @Validated @RequestBody StationRequest request){
        CreateStationResponse response = stationService.createStation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShowStationResponse>> getStations(){
        List<ShowStationResponse> response = stationService.getStations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable("id") Long id){
        stationService.deleteStation(id);
    }

}
