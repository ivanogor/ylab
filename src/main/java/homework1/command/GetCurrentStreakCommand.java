package homework1.command;

import homework1.dto.GetCurrentStreakDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class GetCurrentStreakCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public GetCurrentStreakCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();

        GetCurrentStreakDto getCurrentStreakDto = new GetCurrentStreakDto(userHolder.getUser(), habitName);
        int streak = habitService.getCurrentStreak(getCurrentStreakDto);
        System.out.println("Current streak: " + streak);
    }
}