package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dto.task.TaskPreviewDTO;
import lpnu.vlpi.avpz.model.TaskModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskPreviewConverter implements Converter<TaskModel, TaskPreviewDTO> {

    @Override
    public TaskPreviewDTO convert(TaskModel source) {
        TaskPreviewDTO taskPreviewDTO = new TaskPreviewDTO();
        taskPreviewDTO.setUid(source.getUid());
        taskPreviewDTO.setDisplayName(source.getDisplayName());
        taskPreviewDTO.setTopicDisplayName(source.getTopic().getDisplayName());
        taskPreviewDTO.setLevel(source.getLevel());
        return taskPreviewDTO;
    }
}
