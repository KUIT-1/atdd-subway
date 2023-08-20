package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.request.line.LineRequest;
import kuit.subway.response.line.CreateLineResponse;
import kuit.subway.response.line.ShowLineResponse;
import kuit.subway.utils.exception.LineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuit.subway.utils.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LineService {
    private final LineRepository lineRepository;
    private final StationService stationService;

    @Transactional
    public CreateLineResponse createLine(LineRequest request) {
        Station downStation = stationService.findById(request.getDownStationId());
        Station upStation = stationService.findById(request.getUpStationId());

        checkDuplicateName(request.getName());

        Line line = Line.builder()
                .downStation(downStation)
                .upStation(upStation)
                .distance(request.getDistance())
                .name(request.getName())
                .color(request.getColor())
                .build();

        lineRepository.save(line);

        return CreateLineResponse.from(line);
    }

    public ShowLineResponse getLine(Long id) {
        Line line = findById(id);
        return ShowLineResponse.from(line);
    }

    private void checkDuplicateName(String name) {
        if(lineRepository.existsLineByName(name))
            throw new LineException(DUPLICATED_LINE);
    }


}
