package lpnu.vlpi.avpz.converter.evaluation;

import lpnu.vlpi.avpz.dto.result.AnswerDto;
import lpnu.vlpi.avpz.model.CategoryModel;
import lpnu.vlpi.avpz.model.ChosenAnswersModel;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.service.CategoryService;
import lpnu.vlpi.avpz.service.exceptions.VariantService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
public class AnswertDtoConverter implements Converter<List<AnswerDto>, List<ChosenAnswersModel>> {

    private CategoryService categoryService;
    private VariantService variantService;

    public AnswertDtoConverter(CategoryService categoryService, VariantService variantService) {
        this.categoryService = categoryService;
        this.variantService = variantService;
    }

    @Override
    public List<ChosenAnswersModel> convert(List<AnswerDto> source) {
        List<ChosenAnswersModel> res = new ArrayList<>();
        Map<String, List<AnswerDto>> grupedByCategory = source.stream().collect(groupingBy(AnswerDto::getCategoryUid));
        for (String categoryUid : grupedByCategory.keySet()) {
            ChosenAnswersModel chosenAnswersModel = new ChosenAnswersModel();
            CategoryModel categoryModel = categoryService.getByUid(categoryUid);
            List<VariantModel> variantModels = populateVariants(grupedByCategory.get(categoryUid));
            chosenAnswersModel.setCategory(categoryModel);
            chosenAnswersModel.setVariants(variantModels);
            res.add(chosenAnswersModel);
        }
        return res;
    }

    private List<VariantModel> populateVariants(List<AnswerDto> answerDtos) {
        List<VariantModel> variantModels = new ArrayList<>();
        answerDtos.stream().map(a -> variantService.getByUid(a.getUid())).forEach(variantModels::add);
        return variantModels;
    }
}
