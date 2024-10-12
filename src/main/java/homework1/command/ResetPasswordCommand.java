package homework1.command;

import homework1.dto.ResetPasswordDto;
import homework1.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для сброса пароля пользователя.
 * Эта команда позволяет пользователю сбросить свой пароль, указав свой email и новый пароль.
 */
@RequiredArgsConstructor
public class ResetPasswordCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    /**
     * Метод для выполнения команды сброса пароля пользователя.
     * 1. Запрашивает у пользователя email.
     * 2. Запрашивает у пользователя новый пароль.
     * 3. Создает объект ResetPasswordDto с указанными email и новым паролем.
     * 4. Вызывает метод сброса пароля в UserService.
     * 5. Выводит сообщение об успешном сбросе пароля.
     */
    @Override
    public void execute() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto(email, newPassword);
        userService.resetPassword(resetPasswordDto);
        System.out.println("Password reset successfully.");
    }
}