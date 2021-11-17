package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends CrudRepository<StatisticModel, Long> {

    Optional<StatisticModel> findByUserUidAndModuleIsNullAndTaskIsNull(String userUid);
    Optional<StatisticModel> findByUserUidAndModuleUid(String userUid, String moduleUid);
    Optional<StatisticModel> findByUserUidAndTaskUid(String userUid, String taskUid);
}
