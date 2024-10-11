package homework1.command;

import homework1.entity.User;
import homework1.service.UserService;
import homework1.utils.UserHolder;

import java.util.List;

public class GetAllUsersCommand implements Command {
    private final UserService userService;
    private final UserHolder userHolder;

    public GetAllUsersCommand(UserService userService, UserHolder userHolder) {
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        List<User> users = userService.getAllUsers(userHolder.getUser());
        System.out.println("All users:");
        users.forEach(System.out::println);
    }
}