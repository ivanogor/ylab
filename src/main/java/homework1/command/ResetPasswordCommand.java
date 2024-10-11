package homework1.command;

import homework1.dto.ResetPasswordDto;
import homework1.service.UserService;

import java.util.Scanner;

public class ResetPasswordCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public ResetPasswordCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

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