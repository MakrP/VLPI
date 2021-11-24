package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dto.task.TaskAdminDto;
import lpnu.vlpi.avpz.model.TaskModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskAdminConverter implements Converter<TaskModel,TaskAdminDto> {
    @Override
    public TaskAdminDto convert(TaskModel source) {
        TaskAdminDto taskAdminDto = new TaskAdminDto();
        taskAdminDto.setUid(source.getUid());
        taskAdminDto.setDisplayName(source.getDisplayName());
        taskAdminDto.setModuleDisplayName(source.getModule().getDisplayName());
        taskAdminDto.setTopicDisplayName(source.getTopic().getDisplayName());
        return taskAdminDto;
    }
}
