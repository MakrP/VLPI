package lpnu.vlpi.avpz.dao;


import lpnu.vlpi.avpz.model.ModuleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends CrudRepository<ModuleModel, Long> {

}
