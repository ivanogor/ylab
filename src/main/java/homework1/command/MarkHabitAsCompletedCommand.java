package homework1.command;

import homework1.dto.CompletionHabitDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.time.LocalDate;
import java.util.Scanner;

public class MarkHabitAsCompletedCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public MarkHabitAsCompletedCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

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