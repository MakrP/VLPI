package lpnu.vlpi.avpz.service.exceptions;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with not found")
public class StatisticNotFountException extends RuntimeException {
    private static final String message = "Statisitc not found";

    public StatisticNotFountException() {
        super(message);
    }
}
