package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Results", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class ResultModel extends GeneralModel {

    @OneToOne
    @JoinColumn(name = "task_uid")
    private TaskModel task;

    @OneToOne
    @JoinColumn(name = "user_uid")
    private UserModel user;

    private boolean isCompleted;
    private int grade;
    private int completionTime;

//    @OneToMany(mappedBy = "result")
//    private List<ChosenAnswersModel> chosenAnswers;
}
