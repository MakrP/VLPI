package lpnu.vlpi.avpz.dto.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultDTO {
    private String taskUid;
    private List<AnswerDto> answers;
    private String level;
}
