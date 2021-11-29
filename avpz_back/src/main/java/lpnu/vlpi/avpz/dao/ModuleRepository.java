package lpnu.vlpi.avpz.dao;


import lpnu.vlpi.avpz.model.ModuleModel;
import lpnu.vlpi.avpz.model.TopicModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends CrudRepository<ModuleModel, Long> {
    Optional<ModuleModel> findByDisplayName(String displayName);

}
