package homework1.command;

import homework1.dto.UserActionRequestDto;
import homework1.service.UserService;
import homework1.utils.UserHolder;

import java.util.Scanner;

public class BlockUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final UserHolder userHolder;

    public BlockUserCommand(UserService userService, Scanner scanner, UserHolder userHolder) {
        this.userService = userService;
        this.scanner = scanner;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("Enter email of the user to block:");
        String email = scanner.nextLine();

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(userHolder.getUser(), email);
        userService.blockUser(userActionRequestDto);
        System.out.println("User blocked successfully.");
    }
}