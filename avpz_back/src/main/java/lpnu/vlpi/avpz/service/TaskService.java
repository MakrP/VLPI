package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService extends BaseService {
    TaskModel createOrUpdateTask(TaskModel taskModel);
    TaskModel getTaskByUid(String uid);
    List<TaskModel> getTopicTasks(String uid, int page, int size);
    List<TaskModel> getTopicTasksByLevel(String uid, Level level, int page, int size);
    List<TaskModel> getTopics(int page, int size);
    void removeTask(String taskUid);

}
