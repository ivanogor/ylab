package homework1.exception;

/**
 * Исключение, которое выбрасывается, когда пользователь не найден.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     */
    public UserNotFoundException() {
        super("User not found");
    }
}