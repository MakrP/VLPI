package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.VariantModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariantRepository extends CrudRepository<VariantModel, Long> {

    Optional<VariantModel> getByUid(String uid);

    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Variants", nativeQuery = true)
    Long getMaxUid();
}
