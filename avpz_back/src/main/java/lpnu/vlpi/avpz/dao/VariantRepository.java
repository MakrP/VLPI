package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.VariantModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariantRepository extends CrudRepository<VariantModel, Long> {

    Optional<VariantModel> getByUid(String uid);
}
