package homework3.service;

import homework3.dto.*;
import homework3.entity.User;
import homework3.exception.UserAlreadyExistException;
import homework3.exception.UserNotFoundException;
import homework3.exception.WrongPasswordException;
import homework3.exception.NotValidEmailException;
import homework3.exception.NoPermissionsException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Интерфейс для управления пользователями.
 * Предоставляет методы для регистрации, входа, обновления, удаления, сброса пароля, блокировки и разблокировки пользователей.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param userDto Пользователь, которого нужно зарегистрировать.
     * @return true, если пользователь успешно зарегистрирован.
     * @throws UserAlreadyExistException если пользователь с таким email уже существует.
     * @throws NotValidEmailException    если email не соответствует формату.
     */
    OperationResultDto register(UserDto userDto);

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param loginDto Пользователь, который пытается войти в систему.
     * @return Пользователь, если вход выполнен успешно.
     * @throws UserNotFoundException  если пользователь с таким email не найден.
     * @throws WrongPasswordException если пароль неверный.
     */
    OperationResultDto login(LoginDto loginDto);

    /**
     * Обновляет информацию о пользователе.
     *
     * @param updateUserDto DTO с новыми данными для обновления пользователя.
     * @return true, если пользователь успешно обновлен.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     */
    OperationResultDto update(UpdateUserDto updateUserDto);

    /**
     * Удаляет пользователя по email.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для удаления.
     * @return true, если пользователь успешно удален.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на удаление.
     */
    OperationResultDto delete(UserActionRequestDto userActionRequestDto);

    /**
     * Сбрасывает пароль пользователя.
     *
     * @param resetPasswordDto DTO с данными для сброса пароля.
     * @return true, если пароль успешно сброшен.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     */
    OperationResultDto resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * Блокирует пользователя.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для блокировки.
     * @return true, если пользователь успешно заблокирован.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на блокировку.
     */
    OperationResultDto blockUser(UserActionRequestDto userActionRequestDto);

    /**
     * Разблокирует пользователя.
     *
     * @param userActionRequestDto DTO, которое содержит текущего пользователя и email для разблокировки.
     * @return true, если пользователь успешно разблокирован.
     * @throws UserNotFoundException если пользователь с таким email не найден.
     * @throws NoPermissionsException если у текущего пользователя нет прав на разблокировку.
     */
    OperationResultDto unblockUser(UserActionRequestDto userActionRequestDto);

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     * @throws NoPermissionsException если у текущего пользователя нет прав на просмотр списка пользователей.
     */
    OperationResultDto getAllUsers(HttpServletRequest req);

    User findUserByEmail(String email);
}