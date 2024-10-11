package homework1.command;

import homework1.dto.GetHabitsByFrequencyDateDto;
import homework1.entity.Habit;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class GetHabitsByFrequencyCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public GetHabitsByFrequencyCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter frequency (DAILY/WEEKLY):");
        Habit.Frequency frequency = Habit.Frequency.valueOf(scanner.nextLine().toUpperCase());

        GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto = new GetHabitsByFrequencyDateDto(userHolder.getUser(), frequency);
        habitService.getHabitsByFrequency(getHabitsByFrequencyDateDto).forEach(System.out::println);
    }
}