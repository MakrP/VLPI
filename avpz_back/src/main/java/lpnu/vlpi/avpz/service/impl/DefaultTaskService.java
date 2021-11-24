package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.TaskRepository;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.TaskService;
import lpnu.vlpi.avpz.service.exceptions.TaskNotFountException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<TaskModel> getTopicTasks(String uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAllByTopicUid(uid, pageable).getContent();
    }

    @Override
    public List<TaskModel> getTopicTasksByLevel(String uid, Level level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAllByTopicUidAndLevel(uid, level, pageable).getContent();
    }

    @Override
    public List<TaskModel> getTopics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable).getContent();
    }

    @Override
    public String getNewUid() {
        return null;
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }

}
