package kuit.subway.controller;


import kuit.subway.request.line.CreateLineRequest;
import kuit.subway.request.line.UpdateLineRequest;
import kuit.subway.request.section.DeleteSectionRequest;
import kuit.subway.request.section.SectionRequest;
import kuit.subway.response.line.CreateLineResponse;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.service.LineService;
import kuit.subway.utils.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit.subway.utils.BaseResponseStatus.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineController {
    private final LineService lineService;

    @PostMapping
    public BaseResponseEntity<CreateLineResponse> createLine(
                @Validated @RequestBody CreateLineRequest request){
        CreateLineResponse response = lineService.createLine(request);
        return new BaseResponseEntity<>(CREATED_SUCCESS, response);
    }

    @GetMapping("/{id}")
    public BaseResponseEntity<ShowLineResponse> getLine(
            @PathVariable("id") Long id){
        ShowLineResponse response = lineService.getLine(id);
        return new BaseResponseEntity<>(SUCCESS, response);
    }

    @GetMapping
    public BaseResponseEntity<List<ShowLineResponse>> getLines(){
        List<ShowLineResponse> response = lineService.getLines();
        return new BaseResponseEntity<>(SUCCESS, response);
    }

    @PostMapping("/{id}")
    public BaseResponseEntity<ShowLineResponse> updateLine(
            @PathVariable("id") Long id,
            @Validated @RequestBody UpdateLineRequest request){
        ShowLineResponse response = lineService.updateLine(id, request);
        return new BaseResponseEntity<>(SUCCESS, response);
    }

    @DeleteMapping("/{id}")
    public BaseResponseEntity<?> deleteLine(@PathVariable("id") Long id){
        lineService.deleteLine(id);
        return new BaseResponseEntity<>(DELETED_SUCCESS);
    }
    @PostMapping("/{id}/sections")
    public BaseResponseEntity<ShowLineResponse> addSectionToLine(
            @PathVariable("id") Long lineId,
            @Validated @RequestBody SectionRequest request){
        ShowLineResponse response = lineService.addSectionToLine(lineId, request);
        return new BaseResponseEntity<>(REGISTERED_SUCCESS, response);
    }

    @DeleteMapping("/{id}/sections")
    public BaseResponseEntity<?> deleteSection(
            @PathVariable("id") Long id,
            @Validated @RequestBody DeleteSectionRequest request){
        lineService.deleteSection(id, request);
        return new BaseResponseEntity<>(DELETED_SUCCESS);
    }
}
