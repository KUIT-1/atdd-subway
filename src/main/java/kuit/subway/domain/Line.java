package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.request.line.LineRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private Long distance;

    @Column(length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne
    @JoinColumn(name = "down_station_id")
    private Station downStation;


    public void update(LineRequest request, Station upStation, Station downStation){
        this.name = request.getName();
        this.distance = request.getDistance();
        this.color = request.getColor();
        this.upStation = upStation;
        this.downStation = downStation;
    }

}
