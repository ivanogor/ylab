package homework1.command;

import homework1.dto.GenerateUserProgressReportDto;
import homework1.dto.UserProgressReportDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Команда для генерации отчета о прогрессе пользователя.
 * Эта команда позволяет пользователю получить отчет о его прогрессе по привычкам за указанный период.
 */
@RequiredArgsConstructor
public class GenerateUserProgressReportCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды генерации отчета о прогрессе пользователя.
     * 1. Запрашивает у пользователя начальную дату периода.
     * 2. Запрашивает у пользователя конечную дату периода.
     * 3. Создает объект GenerateUserProgressReportDto с текущим пользователем и указанными датами.
     * 4. Вызывает метод генерации отчета о прогрессе в HabitService.
     * 5. Выводит отчет о прогрессе пользователя.
     */
    @Override
    public void execute() {
        System.out.println("Enter start date (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        GenerateUserProgressReportDto generateUserProgressReportDto = new GenerateUserProgressReportDto(userHolder.getUser(), startDate, endDate);
        UserProgressReportDto report = habitService.generateUserProgressReport(generateUserProgressReportDto);
        System.out.println("User Progress Report:");
        report.getHabitProgresses().forEach(hp -> {
            System.out.println("Habit: " + hp.getHabit().getName());
            System.out.println("Streak: " + hp.getStreak());
            System.out.println("Completion Percentage: " + hp.getCompletionPercentage() + "%");
        });
    }
}