package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Sections {
    @Builder.Default
    @OneToMany(mappedBy = "line", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<Section> sections = new ArrayList<>();
}
