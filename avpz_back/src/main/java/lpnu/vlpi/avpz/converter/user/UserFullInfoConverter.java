package lpnu.vlpi.avpz.converter.user;

import lpnu.vlpi.avpz.dto.user.FullUserInfoDto;
import lpnu.vlpi.avpz.model.UserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class UserFullInfoConverter implements Converter<UserModel, FullUserInfoDto> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    @Override
    public FullUserInfoDto convert(UserModel source) {

        FullUserInfoDto dto = new FullUserInfoDto();
        dto.setUid(source.getUid());
        dto.setName(source.getName());
        dto.setSurname(source.getSurname());
        dto.setLogin(source.getLogin());
        dto.setEmail(source.getEmail());
        dto.setBirthday(simpleDateFormat.format(source.getBirthDate()));
        return dto;
    }
}
