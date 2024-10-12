package homework1.command;

import homework1.dto.UserActionRequestDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для блокировки пользователя.
 * Эта команда позволяет блокировать пользователя по его email.
 *
 * @version 1.0
 */
@RequiredArgsConstructor
public class BlockUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды блокировки пользователя.
     * 1. Запрашивает у пользователя email пользователя, которого нужно заблокировать.
     * 2. Создает объект UserActionRequestDto с текущим пользователем и указанным email.
     * 3. Вызывает метод блокировки пользователя в UserService.
     * 4. Выводит сообщение об успешной блокировке.
     */
    @Override
    public void execute() {
        System.out.println("Enter email of the user to block:");
        String email = scanner.nextLine();

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(userHolder.getUser(), email);
        userService.blockUser(userActionRequestDto);
        System.out.println("User blocked successfully.");
    }
}