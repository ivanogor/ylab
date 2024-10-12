package homework1.command;

import homework1.dto.CountHabitCompletionsForPeriodDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для подсчета количества выполнений привычки за определенный период.
 * Эта команда позволяет пользователю узнать, сколько раз он выполнил определенную привычку за указанный период.
 *
 */
@RequiredArgsConstructor
public class CountHabitCompletionsForPeriodCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды подсчета количества выполнений привычки за определенный период.
     * 1. Запрашивает у пользователя название привычки.
     * 2. Запрашивает у пользователя период (DAY/WEEK/MONTH).
     * 3. Создает объект CountHabitCompletionsForPeriodDto с текущим пользователем, названием привычки и указанным периодом.
     * 4. Вызывает метод подсчета выполнений привычки за период в HabitService.
     * 5. Выводит количество выполнений.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();
        System.out.println("Enter period (DAY/WEEK/MONTH):");
        CountHabitCompletionsForPeriodDto.Period period = CountHabitCompletionsForPeriodDto.Period.valueOf(scanner.nextLine().toUpperCase());

        CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto = new CountHabitCompletionsForPeriodDto(userHolder.getUser(), habitName, period);
        long count = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);
        System.out.println("Number of completions: " + count);
    }
}