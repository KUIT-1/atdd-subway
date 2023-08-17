package kuit.subway.line.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import kuit.subway.BaseTimeEntity;
import kuit.subway.station.domain.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String color;

    private Long distance;

    @OneToMany(mappedBy = "line")
    public List<Station> stations = new ArrayList<>();

    @Builder
    public Line(String name, String color, Long distance, List<Station> stations) {
        this.name = name;
        this.color = color;
        this.distance = distance;
        if (stations != null) {
            this.stations = stations;
            addStations(stations);
        }
    }

    private void addStations(List<Station> stations) {
        stations.forEach(station -> station.addLine(this));
    }

    public void updateInfo(String name, String color, Long distance) {
        this.name = name;
        this.color = color;
        this.distance = distance;
    }

    public void updateStations(List<Station> stations) {
        this.stations = stations;
        addStations(stations);
    }
}
