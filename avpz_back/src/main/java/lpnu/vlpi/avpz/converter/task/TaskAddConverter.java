package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dao.ModuleRepository;
import lpnu.vlpi.avpz.dao.TopicRepository;
import lpnu.vlpi.avpz.dto.task.TaskAddDto;
import lpnu.vlpi.avpz.dto.task.VariantAddDTO;
import lpnu.vlpi.avpz.model.CategoryModel;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.TopicModel;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.CategoryService;
import lpnu.vlpi.avpz.service.TopicService;
import lpnu.vlpi.avpz.service.exceptions.VariantService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
public class TaskAddConverter implements Converter<TaskAddDto, TaskModel> {
    private CategoryService categoryService;
    private VariantService variantService;
    private TopicRepository topicRepository;
    private TopicService topicService;
    private ModuleRepository moduleRepository;

    public TaskAddConverter(CategoryService categoryService, VariantService variantService, TopicRepository topicRepository, TopicService topicService, ModuleRepository moduleRepository) {
        this.categoryService = categoryService;
        this.variantService = variantService;
        this.topicRepository = topicRepository;
        this.topicService = topicService;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public TaskModel convert(TaskAddDto source) {
        TaskModel res = new TaskModel();
        Map<String, List<VariantAddDTO>> categoryVariant = source.getVariants().stream().collect(groupingBy(dto -> dto.getCategoryAnswer()));
        populateVariants(res,categoryVariant);
        populateModule(res,source);
        populateTopic(res,source);
        res.setDisplayName(source.getName());
        res.setExecutionTime(source.getTime());
        res.setLevel(Level.valueOf(source.getLevel()));
        return res;
    }

    private void populateModule(TaskModel res, TaskAddDto source) {
        res.setModule(moduleRepository.findByDisplayName(source.getModule()).get());
    }


    private void populateTopic(TaskModel res, TaskAddDto source) {
        TopicModel topicModel = new TopicModel();
        topicModel.setDisplayName(source.getTopic());
        topicModel.setUid(topicService.getNewUid());
        res.setTopic(topicRepository.save(topicModel));
    }

    private void populateVariants(TaskModel taskModel, Map<String, List<VariantAddDTO>> categoryVariants) {
        List<VariantModel> variants = new ArrayList<>();
        List<CategoryModel> categories = new ArrayList<>();
        for (String categoryName : categoryVariants.keySet()) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setUid(categoryService.getNewUid());
            categoryModel.setDisplayName(categoryName);
            categoryModel = categoryService.createCategory(categoryModel);
            categories.add(categoryModel);
            for (VariantAddDTO variantAddDTO : categoryVariants.get(categoryName)) {
                VariantModel variantModel = new VariantModel();
                variantModel.setUid(variantService.getNewUid());
                variantModel.setDisplayName(variantAddDTO.getDisplayName());
                variantModel.setTooltip(variantAddDTO.getTooltip());
                variantModel.setCategory(categoryModel);
                variants.add(variantService.create(variantModel));
            }
        }
        taskModel.setCategories(categories);
        taskModel.setVariants(variants);
    }
}
