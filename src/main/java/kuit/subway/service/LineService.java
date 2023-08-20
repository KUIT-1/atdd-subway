package kuit.subway.service;
import kuit.subway.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class LineService {
    private final LineRepository lineRepository;
}
