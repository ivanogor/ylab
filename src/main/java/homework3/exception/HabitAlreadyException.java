package homework3.exception;

public class HabitAlreadyException extends RuntimeException {
    public HabitAlreadyException() {
        super("Habit already exists");
    }
}
