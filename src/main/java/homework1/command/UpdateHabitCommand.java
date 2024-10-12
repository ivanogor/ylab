package homework1.command;

import homework1.dto.UpdateHabitDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для обновления информации о привычке.
 * Эта команда позволяет пользователю обновить название, описание или частоту выполнения привычки.
 */
@RequiredArgsConstructor
public class UpdateHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды обновления информации о привычке.
     * 1. Запрашивает у пользователя старое название привычки.
     * 2. Запрашивает у пользователя новое название привычки (оставляет пустым, если не нужно менять).
     * 3. Запрашивает у пользователя новое описание привычки (оставляет пустым, если не нужно менять).
     * 4. Запрашивает у пользователя новую частоту выполнения привычки (оставляет пустым, если не нужно менять).
     * 5. Создает объект UpdateHabitDto с текущим пользователем, старым названием привычки и новыми данными.
     * 6. Вызывает метод обновления привычки в HabitService.
     * 7. Выводит сообщение об успешном обновлении привычки.
     */
    @Override
    public void execute() {
        System.out.println("Enter old habit name:");
        String oldHabitName = scanner.nextLine();
        System.out.println("Enter new habit name (leave empty to skip):");
        String newHabitName = scanner.nextLine();
        System.out.println("Enter new habit description (leave empty to skip):");
        String newHabitDescription = scanner.nextLine();
        System.out.println("Enter new habit frequency (DAILY/WEEKLY, leave empty to skip):");
        String frequencyStr = scanner.nextLine();
        Habit.Frequency newFrequency = frequencyStr.isEmpty() ? null : Habit.Frequency.valueOf(frequencyStr.toUpperCase());

        UpdateHabitDto updateHabitDto = new UpdateHabitDto(userHolder.getUser(), oldHabitName, newHabitName, newHabitDescription, newFrequency);
        habitService.updateHabit(updateHabitDto);
        System.out.println("Habit updated successfully.");
    }
}