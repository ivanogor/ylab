package homework1.command;

import homework1.dto.CompletionHabitDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Команда для отметки привычки как выполненной.
 * Эта команда позволяет пользователю отметить, что он выполнил определенную привычку на указанную дату.
 */
@RequiredArgsConstructor
public class MarkHabitAsCompletedCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды отметки привычки как выполненной.
     * 1. Запрашивает у пользователя название привычки.
     * 2. Запрашивает у пользователя дату выполнения привычки.
     * 3. Создает объект CompletionHabitDto с текущим пользователем, названием привычки и указанной датой.
     * 4. Вызывает метод отметки привычки как выполненной в HabitService.
     * 5. Выводит сообщение об успешной отметке привычки как выполненной.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();
        System.out.println("Enter completion date (YYYY-MM-DD):");
        LocalDate completionDate = LocalDate.parse(scanner.nextLine());

        CompletionHabitDto completionHabitDto = new CompletionHabitDto(userHolder.getUser(), habitName, completionDate);
        habitService.markHabitAsCompleted(completionHabitDto);
        System.out.println("Habit marked as completed successfully.");
    }
}