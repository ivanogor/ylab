package homework1.command;

import homework1.entity.User;
import homework1.service.UserService;
import homework1.utils.UserHolder;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Команда для получения списка всех пользователей.
 * Эта команда позволяет пользователю увидеть список всех зарегистрированных пользователей.
 */
@RequiredArgsConstructor
public class GetAllUsersCommand implements Command {
    private final UserService userService;
    private final UserHolder userHolder;

    /**
     * Метод для выполнения команды получения списка всех пользователей.
     * 1. Вызывает метод получения всех пользователей в UserService.
     * 2. Выводит список всех пользователей.
     */
    @Override
    public void execute() {
        List<User> users = userService.getAllUsers(userHolder.getUser());
        System.out.println("All users:");
        users.forEach(System.out::println);
    }
}