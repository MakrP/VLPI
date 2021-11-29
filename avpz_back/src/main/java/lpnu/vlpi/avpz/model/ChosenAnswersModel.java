package lpnu.vlpi.avpz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ChosenAnswers", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class ChosenAnswersModel extends GeneralModel {
    @OneToOne
    @JoinColumn(name = "category_uid")
    private CategoryModel category;


    @ElementCollection
    private List<VariantModel> variants;

    @ManyToOne
    @JoinColumn(name = "result_uid")
    private ResultModel result;
}
