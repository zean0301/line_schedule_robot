package notify.wooper.exception;

public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException() {
        super("Invalid user_id or password");
    }
}