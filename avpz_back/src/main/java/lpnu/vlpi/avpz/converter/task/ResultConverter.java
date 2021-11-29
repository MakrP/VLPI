package lpnu.vlpi.avpz.converter.task;

import lpnu.vlpi.avpz.dto.task.TaskDTO;
import lpnu.vlpi.avpz.dto.task.TaskPreviewDTO;
import lpnu.vlpi.avpz.dto.task.enums.TaskStatus;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ResultConverter implements Converter<ResultModel, TaskPreviewDTO> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

    @Autowired
    private Converter<TaskModel, TaskPreviewDTO> taskConverter;

    @Override
    public TaskPreviewDTO convert(ResultModel source) {
        TaskPreviewDTO dto = taskConverter.convert(source.getTask());
        dto.setCompletionDate(simpleDateFormat.format(source.getCompletitionDate()));
        dto.setGrade(source.getGrade());
        dto.setTaskStatus(source.isCompleted() ? TaskStatus.FINISHED.getStatus() : TaskStatus.NOT_STARTED.getStatus());
        return dto;
    }
}
