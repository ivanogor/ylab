package homework1.command;

import homework1.dto.DeleteHabitDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class DeleteHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public DeleteHabitCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter habit name to delete:");
        String habitName = scanner.nextLine();

        DeleteHabitDto deleteHabitDto = new DeleteHabitDto(userHolder.getUser(), habitName);
        habitService.deleteHabit(deleteHabitDto);
        System.out.println("Habit deleted successfully.");
    }
}