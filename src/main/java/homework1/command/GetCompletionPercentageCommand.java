package homework1.command;

import homework1.dto.GetCompletionPercentageDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Команда для получения процента выполнения привычки за определенный период.
 * Эта команда позволяет пользователю узнать, насколько успешно он выполнял определенную привычку за указанный период.
 */
@RequiredArgsConstructor
public class GetCompletionPercentageCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения процента выполнения привычки.
     * 1. Запрашивает у пользователя название привычки.
     * 2. Запрашивает у пользователя начальную дату периода.
     * 3. Запрашивает у пользователя конечную дату периода.
     * 4. Создает объект GetCompletionPercentageDto с текущим пользователем, названием привычки и указанными датами.
     * 5. Вызывает метод получения процента выполнения привычки в HabitService.
     * 6. Выводит процент выполнения привычки.
     */
    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();
        System.out.println("Enter start date (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        GetCompletionPercentageDto getCompletionPercentageDto = new GetCompletionPercentageDto(userHolder.getUser(), habitName, startDate, endDate);
        double percentage = habitService.getCompletionPercentage(getCompletionPercentageDto);
        System.out.println("Completion percentage: " + percentage + "%");
    }
}