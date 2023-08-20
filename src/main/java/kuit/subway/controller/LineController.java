package kuit.subway.controller;
import kuit.subway.request.line.LineRequest;
import kuit.subway.response.line.CreateLineResponse;
import kuit.subway.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
}
