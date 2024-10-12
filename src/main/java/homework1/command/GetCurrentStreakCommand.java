package homework1.command;

import homework1.dto.GetCurrentStreakDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для получения текущей серии выполнений привычки.
 * Эта команда позволяет пользователю узнать, сколько дней подряд он выполняет определенную привычку.
 */
@RequiredArgsConstructor
public class GetCurrentStreakCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения текущей серии выполнений привычки.
     * 1. Запрашивает у пользователя название привычки.
     * 2. Создает объект GetCurrentStreakDto с текущим пользователем и указанным названием привычки.
     * 3. Вызывает метод получения текущей серии выполнений в HabitService.
     * 4. Выводит текущую серию выполнений.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();

        GetCurrentStreakDto getCurrentStreakDto = new GetCurrentStreakDto(userHolder.getUser(), habitName);
        int streak = habitService.getCurrentStreak(getCurrentStreakDto);
        System.out.println("Current streak: " + streak);
    }
}