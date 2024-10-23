package homework1.controller;

import homework1.model.dto.*;
import homework1.model.entity.User;
import homework1.model.service.UserService;
import homework1.view.ConsoleView;

import java.util.List;

/**
 * Класс UserController отвечает за обработку всех операций, связанных с пользователями.
 * Он координирует между представлением и сервисом пользователей для выполнения операций,
 * таких как регистрация, вход в систему, обновление, удаление, блокировка и разблокировка пользователей.
 */
public class UserController {
    private final UserService userService;
    private final ConsoleView view;

    /**
     * Конструирует новый экземпляр UserController и инициализирует необходимые сервисы
     * и компоненты представления.
     *
     * @param userService Сервис пользователей.
     */
    public UserController(UserService userService) {
        this.userService = userService;
        this.view = new ConsoleView();
    }

    /**
     * Регистрирует нового пользователя, собирая ввод пользователя и создавая новый объект User.
     *
     * @return Новый зарегистрированный объект User.
     */
    public User registerUser() {
        String name = view.getUserInput("Enter name:");
        String email = view.getUserInput("Enter email:");
        String password = view.getUserInput("Enter password:");

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(User.Role.USER)
                .isBlocked(false)
                .build();

        userService.register(user);
        view.displayMessage("User registered successfully.");
        return user;
    }

    /**
     * Входит в систему пользователя, собирая email и пароль и проверяя учетные данные.
     *
     * @return Объект User, если вход выполнен успешно, иначе null.
     */
    public User loginUser() {
        String email = view.getUserInput("Enter email:");
        String password = view.getUserInput("Enter password:");

        LoginDto loginDto = new LoginDto(email, password);
        User user = userService.login(loginDto);
        view.displayMessage("Logged in successfully.");
        return user;
    }

    /**
     * Обновляет информацию текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void updateUser(User currentUser) {
        String newName = view.getUserInput("Enter new name (leave empty to skip):");
        String newEmail = view.getUserInput("Enter new email (leave empty to skip):");
        String newPassword = view.getUserInput("Enter new password (leave empty to skip):");

        UpdateUserDto updateUserDto = new UpdateUserDto(currentUser.getEmail(), newName, newEmail, newPassword);
        userService.update(updateUserDto);
        view.displayMessage("User updated successfully.");
    }

    /**
     * Удаляет пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void deleteUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to delete:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.delete(userActionRequestDto);
        view.displayMessage("User deleted successfully.");
    }

    /**
     * Сбрасывает пароль пользователя на основе предоставленного email и нового пароля.
     */
    public void resetPassword() {
        String email = view.getUserInput("Enter email:");
        String newPassword = view.getUserInput("Enter new password:");

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto(email, newPassword);
        userService.resetPassword(resetPasswordDto);
        view.displayMessage("Password reset successfully.");
    }

    /**
     * Блокирует пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void blockUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to block:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.blockUser(userActionRequestDto);
        view.displayMessage("User blocked successfully.");
    }

    /**
     * Разблокирует пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void unblockUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to unblock:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.unblockUser(userActionRequestDto);
        view.displayMessage("User unblocked successfully.");
    }

    /**
     * Получает и отображает всех пользователей в системе.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getAllUsers(User currentUser) {
        List<User> users = userService.getAllUsers(currentUser);
        System.out.println("All users:");
        users.forEach(System.out::println);
    }
}