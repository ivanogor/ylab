package homework1.model.exception;

/**
 * Исключение, которое выбрасывается, когда пользователь с таким email уже существует.
 */
public class UserAlreadyExistException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     */
    public UserAlreadyExistException() {
        super("User already exists");
    }
}