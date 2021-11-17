package lpnu.vlpi.avpz.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with not found")
public class UserNotFountException extends RuntimeException {
    private static final String message = "User with uid %s not found";

    public UserNotFountException(String uid) {
        super(String.format(message, uid));
    }
}
