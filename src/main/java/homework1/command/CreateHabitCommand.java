package homework1.command;

import homework1.dto.CreateHabitDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Команда для создания новой привычки.
 * Эта команда позволяет пользователю создать новую привычку, указав её название, описание и частоту.
 */
@RequiredArgsConstructor
public class CreateHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды создания привычки.
     * 1. Запрашивает у пользователя название привычки.
     * 2. Запрашивает у пользователя описание привычки.
     * 3. Запрашивает у пользователя частоту выполнения привычки (DAILY/WEEKLY).
     * 4. Создает объект Habit с указанными параметрами и текущей датой создания.
     * 5. Создает объект CreateHabitDto с текущим пользователем и созданной привычкой.
     * 6. Вызывает метод создания привычки в HabitService.
     * 7. Выводит сообщение об успешном создании привычки.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String name = scanner.nextLine();
        System.out.println("Enter habit description:");
        String description = scanner.nextLine();
        System.out.println("Enter habit frequency (DAILY/WEEKLY):");
        Habit.Frequency frequency = Habit.Frequency.valueOf(scanner.nextLine().toUpperCase());

        Habit habit = Habit.builder()
                .name(name)
                .description(description)
                .frequency(frequency)
                .creationDate(LocalDate.now())
                .isCompleted(false)
                .build();

        CreateHabitDto createHabitDto = new CreateHabitDto(userHolder.getUser(), habit);
        habitService.createHabit(createHabitDto);
        System.out.println("Habit created successfully.");
    }
}