package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public LineDto addSection(Long lineId, SectionCreateRequest req) {

        Long upStationId = req.getUpStationId();
        Long downStationId = req.getDownStationId();

        // 역에 대한 예외처리
        Station upStation = validateStationExist(upStationId);
        Station downStation = validateStationExist(downStationId);
        validateSameStation(upStationId, downStationId);

        // 노선에 대한 예외처리
        Line line = validateLineExist(lineId);

        // 노선에는 구간 형태로 추가해줘야한다.
        line.addSection(Section.createSection(line, upStation, downStation, req.getDistance()));

        return LineDto.createLineDto(line.getId(), line.getName(), line.getColor(), line.getDistance(), getStationDtoList(line.getSections().getOrderSections()));
    }

    @Transactional
    public LineDto deleteSection(Long lineId, SectionDeleteRequest req) {

        Long deleteStationId = req.getDeleteStationId();

        // 역에 대한 예외처리
        Station station = validateStationExist(deleteStationId);

        // 노선에 대한 예외처리
        Line line = validateLineExist(lineId);

        // 노선의 구간 삭제
        line.deleteSection(station);

        return LineDto.createLineDto(line.getId(), line.getName(), line.getColor(), line.getDistance(), getStationDtoList(line.getSections().getOrderSections()));
    }
    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }

    // 노선으로 등록할 두 역이 같은 역인지 판별해주는 함수
    private void validateSameStation(Long downStationId, Long upStationId) {
        if (downStationId == upStationId) {
            throw new InvalidLineStationException();
        }
    }

    // 존재하는 노선인지 판별해주는 함수
    private Line validateLineExist(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(NotFoundLineException::new);
    }

    private List<StationDto> getStationDtoList(List<Section> sections) {

        List<StationDto> result = new ArrayList<>();
        Long nextUpStationId;

        // 맨 처음 첫 구간은 상행, 하행 둘 다 삽입
        Station upStation = sections.get(0).getUpStation();
        result.add(StationDto.createStationDto(upStation.getId(), upStation.getName()));

        Station downStation = sections.get(0).getDownStation();
        result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));

        nextUpStationId = downStation.getId();

        for (int i = 0; i < sections.size() - 1; i++) {
            Long finalNextUpStationId = nextUpStationId;
            Section findSection = sections.stream()
                    .filter(section -> section.getUpStation().getId().equals(finalNextUpStationId))
                    .findFirst().get();
            System.out.println(findSection.getDownStation().getId());
            downStation = findSection.getDownStation();
            result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));
            nextUpStationId = downStation.getId();
        }

        return result;
    }
}
