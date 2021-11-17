package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.enums.Level;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskModel, Long> {
    List<TaskModel> findAllByTopicUid(String topicUid);

    List<TaskModel> findAllByTopicUidAndLevel(String topicUid, Level level);

    Optional<TaskModel> findByUid(String uid);
}
