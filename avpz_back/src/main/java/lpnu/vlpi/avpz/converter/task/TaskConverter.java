package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dto.task.TaskDTO;
import lpnu.vlpi.avpz.model.TaskModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<TaskModel, TaskDTO> {
    @Override
    public TaskDTO convert(TaskModel source) {
        TaskDTO taskDTO = new TaskDTO();
        return taskDTO;
    }
}
