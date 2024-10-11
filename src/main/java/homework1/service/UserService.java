package homework1.service;

import homework1.dto.UserActionRequestDto;
import homework1.dto.LoginDto;
import homework1.dto.ResetPasswordDto;
import homework1.dto.UpdateUserDto;
import homework1.entity.User;
import homework1.exception.UserAlreadyExistException;
import homework1.exception.UserNotFoundException;
import homework1.exception.WrongPasswordException;

import java.util.List;

/**
 * Интерфейс для управления пользователями.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param user Пользователь, которого нужно зарегистрировать.
     * @return true, если пользователь успешно зарегистрирован.
     * @throws UserAlreadyExistException если пользователь с таким email уже существует.
     */
    boolean register(User user);

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param loginDto Пользователь, который пытается войти в систему.
     * @return true, если пользователь успешно вошел в систему.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws WrongPasswordException если пароль неверный.
     */
    User login(LoginDto loginDto);

    /**
     * Обновляет информацию о пользователе.
     *
     * @param updateUserDto DTO с новыми данными для обновления пользователя.
     * @return true, если пользователь успешно обновлен.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     */
    boolean update(UpdateUserDto updateUserDto);

    /**
     * Удаляет пользователя по email.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для удаления.
     * @return true, если пользователь успешно удален.
     */
    boolean delete(UserActionRequestDto userActionRequestDto);

    /**
     * Сбрасывает пароль пользователя.
     *
     * @param resetPasswordDto DTO с данными для сброса пароля.
     * @return true, если пароль успешно сброшен.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     */
    boolean resetPassword(ResetPasswordDto resetPasswordDto);

    boolean blockUser(UserActionRequestDto userActionRequestDto);

    boolean unblockUser(UserActionRequestDto userActionRequestDto);

    List<User> getAllUsers(User currentUser);
}
