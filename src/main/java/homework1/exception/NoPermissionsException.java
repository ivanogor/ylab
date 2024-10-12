package homework1.exception;

/**
 * Исключение, которое выбрасывается, когда у пользователя нет прав на выполнение определенного действия.
 */
public class NoPermissionsException extends RuntimeException {
    /**
     * Конструктор исключения с предопределенным сообщением.
     */
    public NoPermissionsException() {
        super("Does not have permissions to this action");
    }
}