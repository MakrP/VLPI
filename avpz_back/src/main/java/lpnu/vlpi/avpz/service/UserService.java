package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.dto.user.UserUpdateDTO;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Role;
import org.apache.catalina.User;

import java.text.ParseException;
import java.util.List;

public interface UserService extends BaseService {
    UserModel doAuthorize(UserLoginDto userLoginDto);
    UserModel register(UserModel user);
    UserModel updateUserInfo(UserUpdateDTO user) throws ParseException;
    void assignRoleToUser(String userUid, Role role);
    UserModel getUserByUid(String uid);
    List<UserModel> getUsers(int pageN, int pageSize);
    void removeUser(String uid);
}
