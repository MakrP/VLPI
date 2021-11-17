package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.TopicModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<TopicModel, Long> {
    void deleteByUid(String uid);

    Optional<TopicModel> findByUid(String topicUid);
}
