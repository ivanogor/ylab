package homework1.command;

import homework1.dto.UpdateHabitDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class UpdateHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public UpdateHabitCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter old habit name:");
        String oldHabitName = scanner.nextLine();
        System.out.println("Enter new habit name (leave empty to skip):");
        String newHabitName = scanner.nextLine();
        System.out.println("Enter new habit description (leave empty to skip):");
        String newHabitDescription = scanner.nextLine();
        System.out.println("Enter new habit frequency (DAILY/WEEKLY, leave empty to skip):");
        String frequencyStr = scanner.nextLine();
        Habit.Frequency newFrequency = frequencyStr.isEmpty() ? null : Habit.Frequency.valueOf(frequencyStr.toUpperCase());

        UpdateHabitDto updateHabitDto = new UpdateHabitDto(userHolder.getUser(), oldHabitName, newHabitName, newHabitDescription, newFrequency);
        habitService.updateHabit(updateHabitDto);
        System.out.println("Habit updated successfully.");
    }
}