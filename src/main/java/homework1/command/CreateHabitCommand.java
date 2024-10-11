package homework1.command;

import homework1.dto.CreateHabitDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.time.LocalDate;
import java.util.Scanner;

public class CreateHabitCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public CreateHabitCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter habit name:");
        String name = scanner.nextLine();
        System.out.println("Enter habit description:");
        String description = scanner.nextLine();
        System.out.println("Enter habit frequency (DAILY/WEEKLY):");
        Habit.Frequency frequency = Habit.Frequency.valueOf(scanner.nextLine().toUpperCase());

        Habit habit = Habit.builder()
                .name(name)
                .description(description)
                .frequency(frequency)
                .creationDate(LocalDate.now())
                .isCompleted(false)
                .build();

        CreateHabitDto createHabitDto = new CreateHabitDto(userHolder.getUser(), habit);
        habitService.createHabit(createHabitDto);
        System.out.println("Habit created successfully.");
    }
}