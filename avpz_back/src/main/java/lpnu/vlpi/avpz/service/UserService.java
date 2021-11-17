package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Role;

public interface UserService {
    UserModel doAuthorize(UserLoginDto userLoginDto);
    UserModel register(UserModel user);
    UserModel updateUserInfo(UserModel user);
    void assignRoleToUser(String userUid, Role role);
    UserModel getUserByUid(String uid);
}
