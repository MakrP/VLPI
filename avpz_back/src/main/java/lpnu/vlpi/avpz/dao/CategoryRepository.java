package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.CategoryModel;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryModel, Long> {
}
