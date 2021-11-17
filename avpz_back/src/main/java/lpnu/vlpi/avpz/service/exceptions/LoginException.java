package lpnu.vlpi.avpz.service.exceptions;

public class LoginException extends RuntimeException {
    private static final String message = "User with login %s and password %s not exist";

    public LoginException(String login, String password) {
        super(String.format(message, login, password));
    }

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }


}
