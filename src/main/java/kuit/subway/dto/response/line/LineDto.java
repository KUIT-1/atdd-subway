package kuit.subway.dto.response.line;

import jakarta.persistence.Entity;
import kuit.subway.domain.Station;
import kuit.subway.dto.BaseTimeEntity;
import kuit.subway.dto.response.station.StationDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class LineDto extends BaseTimeEntity {
    private Long id;
    private String name;
    private String color;
    private List<StationDto> stations;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public LineDto(Long id, String name, String color, List<StationDto> stations, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.id = id;
        this.color = color;
        this.name = name;
        this.stations = stations;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}