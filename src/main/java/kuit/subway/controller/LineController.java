package kuit.subway.controller;

import kuit.subway.request.line.LineRequest;
import kuit.subway.response.line.CreateLineResponse;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.service.LineService;
import kuit.subway.utils.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import static kuit.subway.utils.BaseResponseStatus.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineController {
    private final LineService lineService;

    @PostMapping
    public BaseResponseEntity<CreateLineResponse> createLine(
                @Validated @RequestBody LineRequest request){
        CreateLineResponse response = lineService.createLine(request);
        return new BaseResponseEntity<>(CREATED_SUCCESS, response);
    }

    @GetMapping("/{id}")
    public BaseResponseEntity<ShowLineResponse> getLine(
            @PathVariable("id") Long id){
        ShowLineResponse response = lineService.getLine(id);
        return new BaseResponseEntity<>(SUCCESS, response);
    }

}
