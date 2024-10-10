package homework1.exception;

/**
 * Исключение, которое выбрасывается, когда переданный email-адрес не соответствует валидному формату.
 */
public class NotValidEmailException extends RuntimeException {

    /**
     * Конструктор по умолчанию, который инициализирует исключение с сообщением "Email is not valid".
     */
    public NotValidEmailException() {
        super("Email is not valid");
    }
}