package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.TaskRepository;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.TaskService;
import lpnu.vlpi.avpz.service.exceptions.TaskNotFountException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultTaskService implements TaskService {

    private TaskRepository taskRepository;

    public DefaultTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskModel createTask(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    @Override
    public TaskModel getTaskByUid(String uid) {
        Optional<TaskModel> taskModel = taskRepository.findByUid(uid);
        if (taskModel.isEmpty()) {
            throw new TaskNotFountException(uid);
        }
        return taskModel.get();
    }

    @Override
    public List<TaskModel> getTopicTasks(String uid) {
        return taskRepository.findAllByTopicUid(uid);
    }

    @Override
    public List<TaskModel> getTopicTasksByLevel(String uid, Level level) {
        return taskRepository.findAllByTopicUidAndLevel(uid, level);
    }

    @Override
    public String getNewUid() {
        return null;
    }

    @Override
    public int getPagesCount(int size) {
        return 0;
    }
}
