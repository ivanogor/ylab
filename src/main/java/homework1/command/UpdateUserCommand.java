package homework1.command;

import homework1.dto.UpdateUserDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для обновления информации о пользователе.
 * Эта команда позволяет пользователю обновить свое имя, email или пароль.
 */
@RequiredArgsConstructor
public class UpdateUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды обновления информации о пользователе.
     * 1. Запрашивает у пользователя новое имя (оставляет пустым, если не нужно менять).
     * 2. Запрашивает у пользователя новый email (оставляет пустым, если не нужно менять).
     * 3. Запрашивает у пользователя новый пароль (оставляет пустым, если не нужно менять).
     * 4. Создает объект UpdateUserDto с текущим email пользователя и новыми данными.
     * 5. Вызывает метод обновления пользователя в UserService.
     * 6. Выводит сообщение об успешном обновлении пользователя.
     */
    @Override
    public void execute() {
        System.out.println("Enter new name (leave empty to skip):");
        String newName = scanner.nextLine();
        System.out.println("Enter new email (leave empty to skip):");
        String newEmail = scanner.nextLine();
        System.out.println("Enter new password (leave empty to skip):");
        String newPassword = scanner.nextLine();

        UpdateUserDto updateUserDto = new UpdateUserDto(userHolder.getUser().getEmail(), newName, newEmail, newPassword);
        userService.update(updateUserDto);
        System.out.println("User updated successfully.");
    }
}