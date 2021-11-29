package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.ResultModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends CrudRepository<ResultModel, Long> {

    Optional<ResultModel> getByUid(String uid);
    Optional<ResultModel> getByTaskUid(String uid);
    Optional<ResultModel> getByUserUid(String uid);

    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Results", nativeQuery = true)
    Long getMaxUid();
}
