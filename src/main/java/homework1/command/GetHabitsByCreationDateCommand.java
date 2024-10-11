package homework1.command;

import homework1.dto.GetHabitsByCreationDateDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;

import java.time.LocalDate;
import java.util.Scanner;

public class GetHabitsByCreationDateCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public GetHabitsByCreationDateCommand(HabitService habitService, Scanner scanner, UserHolder userHolder) {
        this.habitService = habitService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter creation date (YYYY-MM-DD):");
        LocalDate creationDate = LocalDate.parse(scanner.nextLine());

        GetHabitsByCreationDateDto getHabitsByCreationDateDto = new GetHabitsByCreationDateDto(userHolder.getUser(), creationDate);
        habitService.getHabitsByCreationDate(getHabitsByCreationDateDto).forEach(System.out::println);
    }
}