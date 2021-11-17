package lpnu.vlpi.avpz.model;


import lombok.Getter;
import lombok.Setter;
import lpnu.vlpi.avpz.model.enums.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "uid"))
public class UserModel extends GeneralModel{
    private String name;
    private String surname;
    private int age;
    private Date birthDate;
    private String login;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<StatisticModel> userStatisic;

}
