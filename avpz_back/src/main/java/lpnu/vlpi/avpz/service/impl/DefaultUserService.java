package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.UserRepository;
import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.dto.user.UserUpdateDTO;
import lpnu.vlpi.avpz.helper.EncryptionHelper;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Role;
import lpnu.vlpi.avpz.service.UserService;
import lpnu.vlpi.avpz.service.exceptions.LoginException;
import lpnu.vlpi.avpz.service.exceptions.UserNotFountException;
import lpnu.vlpi.avpz.service.populator.Populator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultUserService implements UserService {

    private UserRepository userRepository;
    private EncryptionHelper encryptionHelper;
    private Populator<UserUpdateDTO, UserModel> updatePopulator;
    public DefaultUserService(UserRepository userRepository, EncryptionHelper encryptionHelper, Populator<UserUpdateDTO, UserModel> updatePopulator) {
        this.userRepository = userRepository;
        this.encryptionHelper = encryptionHelper;
        this.updatePopulator = updatePopulator;
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
    public UserModel updateUserInfo(UserUpdateDTO userDto) throws ParseException {
        Optional<UserModel> userModel = userRepository.findByUid(userDto.getUid());
        if(userModel.isEmpty()) {
            throw new UserNotFountException(userDto.getUid());
        }
        UserModel user = userModel.get();
        updatePopulator.populate(userDto, user);
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
        userRepository.removeByUid(uid);
    }


    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(userRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id + 1);    }

    @Override
    public int getPagesCount(long size) {
        int res = userRepository.getTotalPages(size);
        return res == 0 ? 1 : res;
    }
}
