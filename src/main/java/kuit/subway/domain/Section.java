package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    public void addLine(Line line) {
        this.line = line;
        line.addSection(this);
    }

    public void setDownStation(Station station) {
        this.downStation = station;
    }

    public void setUpStation(Station station) {
        this.upStation = station;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
