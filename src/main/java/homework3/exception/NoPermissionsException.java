package homework3.exception;

public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException() {
        super("No permissions to perform this action");
    }

    public NoPermissionsException(String message) {
        super(message);
    }
}