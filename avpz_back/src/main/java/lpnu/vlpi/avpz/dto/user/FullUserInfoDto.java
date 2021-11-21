package lpnu.vlpi.avpz.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FullUserInfoDto {
    private String uid;
    private String name;
    private String login;
    private String surname;
    private String email;
    private String birthday;
}


