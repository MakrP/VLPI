package lpnu.vlpi.avpz.service.populator;

import lpnu.vlpi.avpz.dto.user.UserUpdateDTO;
import lpnu.vlpi.avpz.helper.EncryptionHelper;
import lpnu.vlpi.avpz.model.UserModel;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class UserUpdatePopulator implements Populator<UserUpdateDTO, UserModel> {
    private SimpleDateFormat bdFormat = new SimpleDateFormat("yyyy-MM-dd");
    private EncryptionHelper encryptionHelper;

    public UserUpdatePopulator(EncryptionHelper encryptionHelper) {
        this.encryptionHelper = encryptionHelper;
    }

    @Override
    public void populate(UserUpdateDTO userUpdateDTO, UserModel userModel) throws ParseException {
        userModel.setName(userUpdateDTO.getName());
        userModel.setSurname(userUpdateDTO.getSurname());
        userModel.setEmail(userUpdateDTO.getEmail());
        userModel.setLogin(userUpdateDTO.getEmail());
        userModel.setBirthDate(bdFormat.parse(userUpdateDTO.getBirthday()));
        String password = userUpdateDTO.getPassword();
        if(password != null) {
            userModel.setPassword(encryptionHelper.encrypt(password));
        }
    }
}
