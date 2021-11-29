package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dto.task.CategoryDTO;
import lpnu.vlpi.avpz.dto.task.TaskDTO;
import lpnu.vlpi.avpz.dto.task.VariantDTO;
import lpnu.vlpi.avpz.model.CategoryModel;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.model.enums.Level;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskConverter implements Converter<TaskModel, TaskDTO> {

    private Converter<CategoryModel, CategoryDTO> categoryDTOConverter;

    private Converter<VariantModel, VariantDTO> variantDTOConverter;

    public TaskConverter(Converter<CategoryModel, CategoryDTO> categoryDTOConverter, Converter<VariantModel, VariantDTO> variantDTOConverter) {
        this.categoryDTOConverter = categoryDTOConverter;
        this.variantDTOConverter = variantDTOConverter;
    }

    @Override
    public TaskDTO convert(TaskModel source) {
        TaskDTO taskDTO = new TaskDTO();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (CategoryModel category : source.getCategories()) {
            categoryDTOList.add(categoryDTOConverter.convert(category));
        }
        taskDTO.setCategoryDtos(categoryDTOList);
        List<VariantDTO> variantDTOS = new ArrayList<>();
        for (VariantModel variant : source.getVariants()) {
            variantDTOS.add(variantDTOConverter.convert(variant));
        }
        taskDTO.setVariantDTOList(variantDTOS);
        taskDTO.setLevel(source.getLevel().toString());
        taskDTO.setTime(source.getExecutionTime());
        return taskDTO;
    }
}
