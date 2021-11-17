package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.UserRepository;
import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.helper.EncryptionHelper;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Role;
import lpnu.vlpi.avpz.service.UserService;
import lpnu.vlpi.avpz.service.exceptions.LoginException;
import lpnu.vlpi.avpz.service.exceptions.UserNotFountException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;
    private EncryptionHelper encryptionHelper;

    public DefaultUserService(UserRepository userRepository, EncryptionHelper encryptionHelper) {
        this.userRepository = userRepository;
        this.encryptionHelper = encryptionHelper;
    }

    @Override
    public UserModel doAuthorize(UserLoginDto userLoginDto) {
        String login = userLoginDto.getLogin();
        String password = userLoginDto.getPassword();
        Optional<UserModel> userModel = userRepository.findByLoginAndPassword(login ,
                encryptionHelper.encrypt(password));
        if (userModel.isEmpty()) {
            throw new LoginException(login, password);
        }
        return userModel.get();
    }

    @Override
    public UserModel register(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUserInfo(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(String userUid, Role role) {
        Optional<UserModel> userModel = userRepository.findByUid(userUid);
        if (userModel.isEmpty()) {
            throw new UserNotFountException(userUid);
        }
        UserModel user = userModel.get();
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserModel getUserByUid(String uid) {
        Optional<UserModel> userModel = userRepository.findByUid(uid);
        if (userModel.isEmpty()) {
            throw new UserNotFountException(uid);
        }
        UserModel user = userModel.get();
        return user;
    }
}
