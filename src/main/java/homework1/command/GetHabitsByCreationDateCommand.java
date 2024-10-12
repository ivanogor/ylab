package homework1.command;

import homework1.dto.GetHabitsByCreationDateDto;
import homework1.service.HabitService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Команда для получения списка привычек по дате создания.
 * Эта команда позволяет пользователю увидеть все привычки, созданные в указанную дату.
 */
@RequiredArgsConstructor
public class GetHabitsByCreationDateCommand implements Command {
    private final HabitService habitService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения списка привычек по дате создания.
     * 1. Запрашивает у пользователя дату создания привычек.
     * 2. Создает объект GetHabitsByCreationDateDto с текущим пользователем и указанной датой.
     * 3. Вызывает метод получения привычек по дате создания в HabitService.
     * 4. Выводит список привычек, созданных в указанную дату.
     */
    @Override
    public void execute() {
        System.out.println("Enter creation date (YYYY-MM-DD):");
        LocalDate creationDate = LocalDate.parse(scanner.nextLine());

        GetHabitsByCreationDateDto getHabitsByCreationDateDto = new GetHabitsByCreationDateDto(userHolder.getUser(), creationDate);
        habitService.getHabitsByCreationDate(getHabitsByCreationDateDto).forEach(System.out::println);
    }
}