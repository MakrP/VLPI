package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.CategoryModel;

public interface CategoryService extends BaseService {
    CategoryModel getByUid(String uid);
    CategoryModel createCategory(CategoryModel categoryModel);
}
