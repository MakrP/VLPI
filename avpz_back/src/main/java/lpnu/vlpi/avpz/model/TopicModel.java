package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Topics", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class TopicModel extends GeneralModel{

    private String displayName;
    @OneToMany(mappedBy = "topic")
    private List<TaskModel> tasks;
    @ManyToOne
    @JoinColumn(name = "module_uid")
    private ModuleModel module;

}
