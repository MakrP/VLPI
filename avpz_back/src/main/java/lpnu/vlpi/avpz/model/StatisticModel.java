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
    @OneToOne
    @JoinColumn(name = "user_uid")
    private UserModel user;
    private float averageTime;
    private float averageMark;
    private int totalTaskComplete;
    private float totaltimeOnTasks;
}
