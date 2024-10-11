package homework1.command;

import homework1.dto.CountHabitCompletionsForPeriodDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class CountHabitCompletionsForPeriodCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public CountHabitCompletionsForPeriodCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();
        System.out.println("Enter period (DAY/WEEK/MONTH):");
        CountHabitCompletionsForPeriodDto.Period period = CountHabitCompletionsForPeriodDto.Period.valueOf(scanner.nextLine().toUpperCase());

        CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto = new CountHabitCompletionsForPeriodDto(userHolder.getUser(), habitName, period);
        long count = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);
        System.out.println("Number of completions: " + count);
    }
}