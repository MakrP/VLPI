package lpnu.vlpi.avpz.dto.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String uid;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String birthdate;
}
