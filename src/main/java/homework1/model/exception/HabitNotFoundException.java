package homework1.model.exception;

/**
 * Исключение, которое выбрасывается, когда привычка не найдена.
 */
public class HabitNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     */
    public HabitNotFoundException() {
        super("Habit not found");
    }
}