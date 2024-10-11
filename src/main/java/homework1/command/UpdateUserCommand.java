package homework1.command;

import homework1.dto.UpdateUserDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class UpdateUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public UpdateUserCommand(UserService userService, Scanner scanner, UserHolder userHolder) {
        this.userService = userService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

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