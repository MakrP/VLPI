package lpnu.vlpi.avpz.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class GeneralModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uid;

    private Date createTime;
    private Date modifiedTime;

    @Override
    public boolean equals(Object o) {
        System.out.println("equals call");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralModel that = (GeneralModel) o;
        return id.equals(that.id) &&
                uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid);
    }
}
