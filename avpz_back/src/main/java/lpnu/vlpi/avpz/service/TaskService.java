package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.enums.Level;

import java.util.List;

public interface TaskService extends BaseService {
    TaskModel createTask(TaskModel taskModel);
    TaskModel getTaskByUid(String uid);
    List<TaskModel> getTopicTasks(String uid);
    List<TaskModel> getTopicTasksByLevel(String uid, Level level);
}
