package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.CategoryRepository;
import lpnu.vlpi.avpz.model.CategoryModel;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.service.CategoryService;
import lpnu.vlpi.avpz.service.exceptions.ModelNotFountException;
import lpnu.vlpi.avpz.service.exceptions.TaskNotFountException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;


    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(categoryRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id + 1);
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }


    @Override
    public CategoryModel getByUid(String uid) {
        Optional<CategoryModel> categoryModel = categoryRepository.getByUid(uid);
        if (categoryModel.isEmpty()) {
            throw new ModelNotFountException(String.format("Category with uid %s not fount", uid));
        }
        return categoryModel.get();
    }

    @Override
    public CategoryModel createCategory(CategoryModel categoryModel) {
        return categoryRepository.save(categoryModel);
    }
}
