package kuit.subway.response.line;


import kuit.subway.domain.Line;
import lombok.Getter;

@Getter
public class CreateLineResponse {
    private Long id;
    private CreateLineResponse(Long id){
        this.id = id;
    }

    public static CreateLineResponse from(Line line){
        return new CreateLineResponse(line.getId());
    }
}
