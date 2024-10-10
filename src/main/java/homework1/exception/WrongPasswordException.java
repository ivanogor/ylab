package homework1.exception;

/**
 * Исключение, которое выбрасывается, когда введен неверный пароль.
 */
public class WrongPasswordException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     */
    public WrongPasswordException() {
        super("Wrong password");
    }
}