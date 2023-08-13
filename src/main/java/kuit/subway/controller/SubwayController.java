package kuit.subway.controller;

import kuit.subway.request.PostStationRequest;
import kuit.subway.response.GetStationsResponse;
import kuit.subway.response.PostStationsResponse;
import kuit.subway.service.SubwayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SubwayController {
    private final SubwayService subwayService;

    @PostMapping("/stations")
    public PostStationsResponse createStation(@RequestBody PostStationRequest postStationRequest){
        Long id = subwayService.createStation(postStationRequest.getName()).getId();
        return new PostStationsResponse(id);
    }

    @GetMapping("/stations")
    public List<GetStationsResponse> createStation(){
        return subwayService.getStations();
    }

}
