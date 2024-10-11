package homework1.command;

import homework1.dto.LoginDto;
import homework1.entity.User;
import homework1.service.UserService;
import lombok.Getter;

import java.util.Scanner;

public class LoginUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    @Getter
    private User currentUser;

    public LoginUserCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

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