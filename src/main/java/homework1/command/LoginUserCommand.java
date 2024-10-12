package homework1.command;

import homework1.dto.LoginDto;
import homework1.entity.User;
import homework1.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для входа пользователя в систему.
 * Эта команда позволяет пользователю войти в систему, указав свой email и пароль.
 */
@RequiredArgsConstructor
public class LoginUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    @Getter
    private User currentUser;

    /**
     * Метод для выполнения команды входа пользователя в систему.
     * 1. Запрашивает у пользователя email.
     * 2. Запрашивает у пользователя пароль.
     * 3. Создает объект LoginDto с указанными email и паролем.
     * 4. Вызывает метод входа в систему в UserService.
     * 5. Выводит сообщение об успешном входе.
     * 6. Устанавливает текущего пользователя.
     */
    @Override
    public void execute() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        LoginDto loginDto = new LoginDto(email, password);
        User user = userService.login(loginDto);
        System.out.println("Logged in successfully.");
        this.currentUser = user;
    }
}