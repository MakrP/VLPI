package lpnu.vlpi.avpz.service.exceptions;

public class TopicNotFountException extends RuntimeException {
    private static final String message = "Topic with uid %s not found";

    public TopicNotFountException(String uid) {
        super(String.format(message, uid));
    }
}
