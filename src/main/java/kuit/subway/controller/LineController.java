package kuit.subway.controller;
import kuit.subway.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineController {
    private final LineService lineService;

}
