package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.user.FullUserInfoDto;
import lpnu.vlpi.avpz.dto.user.MainUserInfoDto;
import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.UserService;
import lpnu.vlpi.avpz.service.exceptions.UserNotFountException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/vlpi/v1/users")
public class UserController {

    private final Converter<UserModel, MainUserInfoDto> userMainInfoConverter;
    private final Converter<UserModel, FullUserInfoDto> fullUserInfoDtoConverter;
    private UserService userService;

    public UserController(UserService userService, @Qualifier("UserToMainInfo") Converter<UserModel, MainUserInfoDto> userToMainUserInfoConverter, Converter<UserModel, FullUserInfoDto> fullUserInfoDtoConverter) {
        this.userService = userService;
        this.userMainInfoConverter = userToMainUserInfoConverter;
        this.fullUserInfoDtoConverter = fullUserInfoDtoConverter;
    }


    @PostMapping("/authorize")
    public ResponseEntity<MainUserInfoDto> doAuthorize(@RequestBody UserLoginDto userLoginDto, HttpSession httpSession) {
        UserModel savedUser = userService.doAuthorize(userLoginDto);
        MainUserInfoDto mainUserInfoDto = userMainInfoConverter.convert(savedUser);
        httpSession.setAttribute("currentUser", savedUser);
        return new ResponseEntity<>(mainUserInfoDto, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<MainUserInfoDto> doRegister(@RequestBody UserModel userModel) {
        UserModel registredUser = userService.register(userModel);
        MainUserInfoDto mainUserInfoDto = userMainInfoConverter.convert(registredUser);
        return new ResponseEntity<>(mainUserInfoDto, HttpStatus.CREATED);
    }

    @GetMapping("/main")
    public ResponseEntity<MainUserInfoDto> getMainInfo(HttpSession httpSession) {
        UserModel currentUser = (UserModel) httpSession.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserNotFountException("1");
        }
        MainUserInfoDto dto = userMainInfoConverter.convert(currentUser);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/full")
    public ResponseEntity<FullUserInfoDto> getFullInfo(HttpSession httpSession) {
        UserModel currentUser = (UserModel) httpSession.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserNotFountException("1");
        }
        FullUserInfoDto result = fullUserInfoDtoConverter.convert(currentUser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/full")
    public ResponseEntity<FullUserInfoDto> getFullInfoByUid(@PathVariable("userId") String userId, HttpSession httpSession) {
        FullUserInfoDto result = fullUserInfoDtoConverter.convert(userService.getUserByUid(userId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<FullUserInfoDto> getCurrentUser(HttpSession httpSession) {
        UserModel currentUser = (UserModel) httpSession.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserNotFountException("1");
        }
        FullUserInfoDto result = fullUserInfoDtoConverter.convert(currentUser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FullUserInfoDto>> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<FullUserInfoDto> users = new ArrayList<>();
        userService.getUsers(page, size).stream().map(fullUserInfoDtoConverter::convert).forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<Integer> getPageSize(@RequestParam("size") long size) {
        return new ResponseEntity<>(userService.getPagesCount(size), HttpStatus.OK);
    }

}
