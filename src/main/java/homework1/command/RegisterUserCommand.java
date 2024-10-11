package homework1.command;

import homework1.entity.User;
import homework1.service.UserService;
import lombok.Getter;

import java.util.Scanner;

public class RegisterUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    @Getter
    private User currentUser;

    public RegisterUserCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

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