package homework1.command;

import homework1.dto.UserActionRequestDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для удаления пользователя.
 * Эта команда позволяет удалить пользователя по его email.
 */
@RequiredArgsConstructor
public class DeleteUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды удаления пользователя.
     * 1. Запрашивает у пользователя email пользователя, которого нужно удалить.
     * 2. Создает объект UserActionRequestDto с текущим пользователем и указанным email.
     * 3. Вызывает метод удаления пользователя в UserService.
     * 4. Выводит сообщение об успешном удалении.
     */
    @Override
    public void execute() {
        System.out.println("Enter email of the user to delete:");
        String email = scanner.nextLine();

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(userHolder.getUser(), email);
        userService.delete(userActionRequestDto);
        System.out.println("User deleted successfully.");
    }
}