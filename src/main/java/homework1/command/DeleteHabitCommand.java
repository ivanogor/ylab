package homework1.command;

import homework1.dto.DeleteHabitDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для удаления привычки.
 * Эта команда позволяет пользователю удалить привычку по её названию.
 */
@RequiredArgsConstructor
public class DeleteHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды удаления привычки.
     * 1. Запрашивает у пользователя название привычки, которую нужно удалить.
     * 2. Создает объект DeleteHabitDto с текущим пользователем и указанным названием привычки.
     * 3. Вызывает метод удаления привычки в HabitService.
     * 4. Выводит сообщение об успешном удалении привычки.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name to delete:");
        String habitName = scanner.nextLine();

        DeleteHabitDto deleteHabitDto = new DeleteHabitDto(userHolder.getUser(), habitName);
        habitService.deleteHabit(deleteHabitDto);
        System.out.println("Habit deleted successfully.");
    }
}