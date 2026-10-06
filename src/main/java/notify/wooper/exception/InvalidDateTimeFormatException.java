package notify.wooper.exception;

public class InvalidDateTimeFormatException extends RuntimeException {

    public InvalidDateTimeFormatException() {
        super("Invalid datetime format, expected: yyyy-MM-dd HH:mm:ss");
    }
}
