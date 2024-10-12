package homework1.command;

import homework1.entity.User;
import homework1.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

/**
 * Команда для регистрации нового пользователя.
 * Эта команда позволяет пользователю зарегистрироваться в системе, указав свое имя, email и пароль.
 */
@RequiredArgsConstructor
public class RegisterUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    @Getter
    private User currentUser;

    /**
     * Метод для выполнения команды регистрации нового пользователя.
     * 1. Запрашивает у пользователя имя.
     * 2. Запрашивает у пользователя email.
     * 3. Запрашивает у пользователя пароль.
     * 4. Создает объект User с указанными данными и ролью USER.
     * 5. Вызывает метод регистрации пользователя в UserService.
     * 6. Выводит сообщение об успешной регистрации.
     * 7. Устанавливает текущего пользователя.
     */
    @Override
    public void execute() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(User.Role.USER)
                .isBlocked(false)
                .build();

        userService.register(user);
        System.out.println("User registered successfully.");
        this.currentUser = user;
    }
}