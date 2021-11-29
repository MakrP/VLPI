package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.ChosenAnswersModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChosenAnswersRepository extends CrudRepository<ChosenAnswersModel, Long> {
    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM ChosenAnswers", nativeQuery = true)
    Long getMaxUid();
}
