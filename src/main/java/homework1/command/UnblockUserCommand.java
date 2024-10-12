package homework1.command;

import homework1.dto.UserActionRequestDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для разблокировки пользователя.
 * Эта команда позволяет разблокировать пользователя по его email.
 */
@RequiredArgsConstructor
public class UnblockUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды разблокировки пользователя.
     * 1. Запрашивает у пользователя email пользователя, которого нужно разблокировать.
     * 2. Создает объект UserActionRequestDto с текущим пользователем и указанным email.
     * 3. Вызывает метод разблокировки пользователя в UserService.
     * 4. Выводит сообщение об успешной разблокировке.
     */
    @Override
    public void execute() {
        System.out.println("Enter email of the user to unblock:");
        String email = scanner.nextLine();

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(userHolder.getUser(), email);
        userService.unblockUser(userActionRequestDto);
        System.out.println("User unblocked successfully.");
    }
}