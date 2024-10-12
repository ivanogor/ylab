package homework1.command;

import homework1.dto.GetHabitsByFrequencyDateDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для получения списка привычек по частоте выполнения.
 * Эта команда позволяет пользователю увидеть все привычки, выполняемые с указанной частотой (DAILY/WEEKLY).
 */
@RequiredArgsConstructor
public class GetHabitsByFrequencyCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения списка привычек по частоте выполнения.
     * 1. Запрашивает у пользователя частоту выполнения привычек (DAILY/WEEKLY).
     * 2. Создает объект GetHabitsByFrequencyDateDto с текущим пользователем и указанной частотой.
     * 3. Вызывает метод получения привычек по частоте выполнения в HabitService.
     * 4. Выводит список привычек, выполняемых с указанной частотой.
     */
    @Override
    public void execute() {
        System.out.println("Enter frequency (DAILY/WEEKLY):");
        Habit.Frequency frequency = Habit.Frequency.valueOf(scanner.nextLine().toUpperCase());

        GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto = new GetHabitsByFrequencyDateDto(userHolder.getUser(), frequency);
        habitService.getHabitsByFrequency(getHabitsByFrequencyDateDto).forEach(System.out::println);
    }
}