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


    @Override
    public String getNewUid() {
        return null;
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
}
