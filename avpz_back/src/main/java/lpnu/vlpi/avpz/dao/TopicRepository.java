package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.TopicModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends CrudRepository<TopicModel, Long> {
    void deleteByUid(String uid);

    Optional<TopicModel> findByUid(String topicUid);


    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Topics", nativeQuery = true)
    Long getMaxUid();

}
