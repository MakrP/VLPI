package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Statistics", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class StatisticModel extends GeneralModel {
    @ManyToOne
    @JoinColumn(name = "user_uid")
    private UserModel user;

    private float averageTime;
    private float averageMark;
    private float averageTimeOnTask;
    private int totalTaskComplete;

    @ManyToOne
    @JoinColumn(name = "module_uid")
    private ModuleModel module;

    @ManyToOne
    @JoinColumn(name = "task_uid")
    private TaskModel task;

}
