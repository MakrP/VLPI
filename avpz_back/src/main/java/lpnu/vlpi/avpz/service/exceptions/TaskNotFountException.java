package lpnu.vlpi.avpz.service.exceptions;

public class TaskNotFountException extends RuntimeException {
    private static final String message = "Task with uid %s not found";

    public TaskNotFountException(String uid) {
        super(String.format(message, uid));
    }
}
