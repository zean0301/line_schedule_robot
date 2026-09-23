package notify.wooper.exception;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException() {
        super("Invalid token");
    }
}