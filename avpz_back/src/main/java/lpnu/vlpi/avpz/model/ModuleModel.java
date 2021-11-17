package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "modules", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class ModuleModel extends GeneralModel {
    private String displayName;

    @OneToMany(mappedBy = "module")
    private List<TopicModel> topics;
}
