package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.CategoryModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryModel, Long> {
    Optional<CategoryModel> getByUid(String uid);

    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Categories", nativeQuery = true)
    Long getMaxUid();
}
