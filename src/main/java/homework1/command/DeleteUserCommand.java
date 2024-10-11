package homework1.command;

import homework1.dto.UserActionRequestDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class DeleteUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public DeleteUserCommand(UserService userService, Scanner scanner, UserHolder userHolder) {
        this.userService = userService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter email of the user to delete:");
        String email = scanner.nextLine();

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(userHolder.getUser(), email);
        userService.delete(userActionRequestDto);
        System.out.println("User deleted successfully.");
    }
}