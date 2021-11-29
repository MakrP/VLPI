package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends CrudRepository<StatisticModel, Long> {

    Optional<StatisticModel> findByUserUid(String userUid);

    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Categories", nativeQuery = true)
    Long getMaxUid();
}
