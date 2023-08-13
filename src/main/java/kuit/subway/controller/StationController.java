package kuit.subway.controller;

import kuit.subway.request.station.PostStationRequest;
import kuit.subway.response.station.GetStationsResponse;
import kuit.subway.response.station.PostStationResponse;
import kuit.subway.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public PostStationResponse createStation(@Validated @RequestBody PostStationRequest postStationRequest){
        return stationService.createStation(postStationRequest.getName());
    }

    @GetMapping
    public List<GetStationsResponse> createStation(){
        return stationService.getStations();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable("id") Long id){
        stationService.deleteStation(id);
    }

}
