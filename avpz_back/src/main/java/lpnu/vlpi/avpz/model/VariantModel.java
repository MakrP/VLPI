package lpnu.vlpi.avpz.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Variants", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class VariantModel extends GeneralModel {
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "task_uid")
    private TaskModel task;

    @ManyToOne
    @JoinColumn(name = "category_uid")
    private CategoryModel category;

    private String tooltip;


}
