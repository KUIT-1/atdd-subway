package kuit.subway.response.line;


import kuit.subway.domain.Line;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class CreateLineResponse {
    Long id;
    private CreateLineResponse(Long id){
        this.id = id;
    }

    public static CreateLineResponse from(Line line){
        return new CreateLineResponse(line.getId());
    }
}
