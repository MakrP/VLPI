package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.ChosenAnswersModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChosenAnswersRepository extends CrudRepository<ChosenAnswersModel, Long> {

}
