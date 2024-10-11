package homework1.command;

import homework1.dto.GenerateUserProgressReportDto;
import homework1.dto.UserProgressReportDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.time.LocalDate;
import java.util.Scanner;

public class GenerateUserProgressReportCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public GenerateUserProgressReportCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

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