package homework1.exception;

public class NoPermissionsException extends RuntimeException{
    public NoPermissionsException() {
        super("Does not have permissions to this action");
    }
}
