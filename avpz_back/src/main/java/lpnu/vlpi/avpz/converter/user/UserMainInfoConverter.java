package lpnu.vlpi.avpz.converter.user;

import lpnu.vlpi.avpz.dto.user.MainUserInfoDto;
import lpnu.vlpi.avpz.model.UserModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Qualifier("UserToMainInfo")
@Component
public class UserMainInfoConverter implements Converter<UserModel, MainUserInfoDto> {
    @Override
    public MainUserInfoDto convert(UserModel source) {
        MainUserInfoDto mainUserInfoDto = new MainUserInfoDto();
        mainUserInfoDto.setUserName(source.getName() + " " + source.getSurname());
        mainUserInfoDto.setUid(source.getUid());
        return mainUserInfoDto;
    }
}
