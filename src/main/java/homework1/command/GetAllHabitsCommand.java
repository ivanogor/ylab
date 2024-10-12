package homework1.command;

import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

/**
 * Команда для получения списка всех привычек пользователя.
 * Эта команда позволяет пользователю увидеть все свои привычки.
 */
@RequiredArgsConstructor
public class GetAllHabitsCommand implements Command {
    private final HabitService habitService;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения списка всех привычек пользователя.
     * 1. Вызывает метод получения всех привычек пользователя в HabitService.
     * 2. Выводит список всех привычек пользователя.
     */
    @Override
    public void execute() {
        System.out.println("All habits:");
        habitService.getAllHabits(userHolder.getUser()).forEach(System.out::println);
    }
}