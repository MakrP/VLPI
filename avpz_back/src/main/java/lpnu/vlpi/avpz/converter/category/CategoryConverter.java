package lpnu.vlpi.avpz.converter.category;

import lpnu.vlpi.avpz.dto.task.CategoryDTO;
import lpnu.vlpi.avpz.model.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<CategoryModel, CategoryDTO> {
    @Override
    public CategoryDTO convert(CategoryModel source) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setUid(source.getUid());
        categoryDTO.setDisplayName(source.getDisplayName());
        return categoryDTO;
    }
}
