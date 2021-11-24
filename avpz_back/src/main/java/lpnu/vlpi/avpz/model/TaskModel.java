package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;
import lpnu.vlpi.avpz.model.enums.Level;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Tasks", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class TaskModel extends GeneralModel {

    private String displayName;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "topic_uid")
    private TopicModel topic;

    @OneToMany(mappedBy = "task")
    private List<VariantModel> variants;

    @OneToMany(mappedBy = "task")
    private List<CategoryModel> categories;

    @ManyToOne
    @JoinColumn(name = "module_uid")
    private ModuleModel module;
}
