package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Categories", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class CategoryModel extends GeneralModel {
    private String displayName;
    @ManyToOne
    @JoinColumn(name = "task_uid")
    private TaskModel task;
}
