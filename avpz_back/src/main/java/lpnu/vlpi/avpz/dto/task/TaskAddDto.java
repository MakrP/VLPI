package lpnu.vlpi.avpz.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskAddDto {
    List<VariantAddDTO> variants;
    private String topic;
    private String module;
    private String name;
    private int time;
    private String level;
}
