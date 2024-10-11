package homework1.command;

import homework1.dto.GetCompletionPercentageDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.time.LocalDate;
import java.util.Scanner;

public class GetCompletionPercentageCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public GetCompletionPercentageCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

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