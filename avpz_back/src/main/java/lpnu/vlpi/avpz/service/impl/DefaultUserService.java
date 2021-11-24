package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.UserRepository;
import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.helper.EncryptionHelper;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Role;
import lpnu.vlpi.avpz.service.UserService;
import lpnu.vlpi.avpz.service.exceptions.LoginException;
import lpnu.vlpi.avpz.service.exceptions.UserNotFountException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        user.setUid(String.valueOf(userRepository.getMaxUid() + 1));
        user.setPassword(encryptionHelper.encrypt(user.getPassword()));
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

    @Override
    public List<UserModel> getUsers(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        Page<UserModel> userModelPage =  userRepository.findAll(pageable);
        return userModelPage.getContent();
    }

    @Override
    public void removeUser(String uid) {
        userRepository.deleteUserModelByUid(uid);
    }


    @Override
    public String getNewUid() {
        return String.valueOf(userRepository.getMaxUid());
    }

    @Override
    public int getPagesCount(long size) {
        int res = userRepository.getTotalPages(size);
        return res == 0 ? 1 : res;
    }
}
