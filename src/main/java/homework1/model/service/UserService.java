package homework1.model.service;

import homework1.model.dto.UserActionRequestDto;
import homework1.model.dto.LoginDto;
import homework1.model.dto.ResetPasswordDto;
import homework1.model.dto.UpdateUserDto;
import homework1.model.entity.User;
import homework1.model.exception.UserAlreadyExistException;
import homework1.model.exception.UserNotFoundException;
import homework1.model.exception.WrongPasswordException;
import homework1.model.exception.NotValidEmailException;
import homework1.model.exception.NoPermissionsException;

import java.util.List;

/**
 * Интерфейс для управления пользователями.
 * Предоставляет методы для регистрации, входа, обновления, удаления, сброса пароля, блокировки и разблокировки пользователей.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param user Пользователь, которого нужно зарегистрировать.
     * @return true, если пользователь успешно зарегистрирован.
     * @throws UserAlreadyExistException если пользователь с таким email уже существует.
     * @throws NotValidEmailException если email не соответствует формату.
     */
    boolean register(User user);

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param loginDto Пользователь, который пытается войти в систему.
     * @return Пользователь, если вход выполнен успешно.
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
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на удаление.
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

    /**
     * Блокирует пользователя.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для блокировки.
     * @return true, если пользователь успешно заблокирован.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на блокировку.
     */
    boolean blockUser(UserActionRequestDto userActionRequestDto);

    /**
     * Разблокирует пользователя.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для разблокировки.
     * @return true, если пользователь успешно разблокирован.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на разблокировку.
     */
    boolean unblockUser(UserActionRequestDto userActionRequestDto);

    /**
     * Возвращает список всех пользователей.
     *
     * @param currentUser Текущий пользователь, который запрашивает список.
     * @return Список всех пользователей.
     * @throws NoPermissionsException если у текущего пользователя нет прав на просмотр списка пользователей.
     */
    List<User> getAllUsers(User currentUser);
}