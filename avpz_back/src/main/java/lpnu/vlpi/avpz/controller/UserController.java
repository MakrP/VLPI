package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.user.FullUserInfoDto;
import lpnu.vlpi.avpz.dto.user.MainUserInfoDto;
import lpnu.vlpi.avpz.dto.user.UserLoginDto;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vlpi/v1/users/")
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
    public ResponseEntity<MainUserInfoDto> doAuthorize(@RequestBody UserLoginDto userLoginDto) {
        UserModel savedUser = userService.doAuthorize(userLoginDto);
        MainUserInfoDto mainUserInfoDto = userMainInfoConverter.convert(savedUser);
        return new ResponseEntity<>(mainUserInfoDto, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<MainUserInfoDto> doRegister(@RequestBody UserModel userModel) {
        UserModel registredUser = userService.register(userModel);
        MainUserInfoDto mainUserInfoDto = userMainInfoConverter.convert(registredUser);
        return new ResponseEntity<>(mainUserInfoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/main")
    public ResponseEntity<MainUserInfoDto> getMainInfo(@PathVariable("userId") String userId) {
        MainUserInfoDto result = userMainInfoConverter.convert(userService.getUserByUid(userId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/full")
    public ResponseEntity<FullUserInfoDto> getFullInfo(@PathVariable("userId") String userId) {
        FullUserInfoDto result = fullUserInfoDtoConverter.convert(userService.getUserByUid(userId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
