package lpnu.vlpi.avpz.dto.task;

import lombok.Getter;
import lombok.Setter;
import lpnu.vlpi.avpz.dto.task.enums.TaskStatus;
import lpnu.vlpi.avpz.model.enums.Level;

import java.util.Date;


@Getter
@Setter
public class TaskAdminDto {
    private String uid;
    private String displayName;
    private String topicDisplayName;
    private String moduleDisplayName;
}
