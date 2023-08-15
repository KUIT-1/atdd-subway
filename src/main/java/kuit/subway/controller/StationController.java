package kuit.subway.controller;

import kuit.subway.request.station.PostStationRequest;
import kuit.subway.response.station.GetStationsResponse;
import kuit.subway.response.station.PostStationResponse;
import kuit.subway.service.StationService;
import kuit.subway.utils.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public BaseResponseEntity<PostStationResponse> createStation(
            @Validated @RequestBody PostStationRequest postStationRequest){
        PostStationResponse postStationResponse = stationService.createStation(postStationRequest.getName());
        return new BaseResponseEntity<>(CREATED_SUCCESS, postStationResponse);
    }

    @GetMapping
    public BaseResponseEntity<List<GetStationsResponse>> getStations(){
        List<GetStationsResponse> response = stationService.getStations();
        return new BaseResponseEntity<>(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponseEntity<?> deleteStation(@PathVariable("id") Long id){
        stationService.deleteStation(id);
        return new BaseResponseEntity<>(DELETED_SUCCESS);
    }

}
