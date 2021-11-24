package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<TaskModel, Long> {
    Page<TaskModel> findAllByTopicUid(String topicUid, Pageable pageable);

    Page<TaskModel> findAllByTopicUidAndLevel(String topicUid, Level level , Pageable pageable);

    Optional<TaskModel> findByUid(String uid);

    @Query(value = "SELECT count(t) / ?1 from TaskModel t")
    int getPagesCount(long size);

}
