package kuit.subway.controller;

import kuit.subway.request.station.StationRequest;
import kuit.subway.response.station.CreateStationResponse;
import kuit.subway.response.station.ShowStationResponse;
import kuit.subway.service.StationService;
import kuit.subway.utils.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit.subway.utils.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    @PostMapping
    public BaseResponseEntity<CreateStationResponse> createStation(
            @Validated @RequestBody StationRequest request){
        CreateStationResponse response = stationService.createStation(request);
        return new BaseResponseEntity<>(CREATED_SUCCESS, response);
    }

    @GetMapping
    public BaseResponseEntity<List<ShowStationResponse>> getStations(){
        List<ShowStationResponse> response = stationService.getStations();
        return new BaseResponseEntity<>(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponseEntity<?> deleteStation(@PathVariable("id") Long id){
        stationService.deleteStation(id);
        return new BaseResponseEntity<>(DELETED_SUCCESS);
    }

}
