package lpnu.vlpi.avpz.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskDTO {
    private List<VariantDTO> variantDTOList;
    private List<CategoryDTO> categoryDtos;
    private String level;
    private int time;
    private String name;
}
