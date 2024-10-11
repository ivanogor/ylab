package homework1.service.impl;

import homework1.dto.UserActionRequestDto;
import homework1.dto.LoginDto;
import homework1.dto.ResetPasswordDto;
import homework1.dto.UpdateUserDto;
import homework1.entity.User;
import homework1.exception.*;
import homework1.repository.UserRepository;
import homework1.service.UserService;
import homework1.utils.PasswordHasher;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Сервис для управления пользователями.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Регулярное выражение для проверки валидности email-адресов.
     */
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public boolean register(User user) {
        String email = user.getEmail();
        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        if (userRepository.isExist(email)) {
            throw new UserAlreadyExistException();
        }
        if (!isValidEmail(email)) {
            throw new NotValidEmailException();
        }
        userRepository.addUser(user);
        return true;
    }

    @Override
    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        String hashedPassword = PasswordHasher.hashPassword(loginDto.getPassword());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new WrongPasswordException();
    }

    @Override
    public boolean update(UpdateUserDto updateUserDto) {
        User user = userRepository.findByEmail(updateUserDto.getEmail());
        if (Objects.nonNull(user)) {

            if (Objects.nonNull(updateUserDto.getNewName())) {
                user.setName(updateUserDto.getNewName());
            }
            if (Objects.nonNull(updateUserDto.getNewEmail())) {
                user.setEmail(updateUserDto.getNewEmail());
            }
            if (Objects.nonNull(updateUserDto.getNewPassword())) {
                user.setPassword(updateUserDto.getNewPassword());
            }

            return true;
        }
        throw new UserNotFoundException();
    }

    @Override
    public boolean delete(UserActionRequestDto userActionRequestDto) {
        String email = userActionRequestDto.getEmailToAction();
        User userToDelete = userRepository.findByEmail(email);
        if (Objects.isNull(userToDelete)) {
            throw new UserNotFoundException();
        }

        User currentUser = userActionRequestDto.getCurrentUser();

        if (currentUser.getRole() == User.Role.ADMIN || currentUser.equals(userToDelete)) {
            return userRepository.deleteUser(email);

        }
        else {
            throw new NoPermissionsException();
        }
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByEmail(resetPasswordDto.getEmail());
        //отправка письма javax.mail, проверка кода и изменение пароля в будущем
        if (Objects.nonNull(user)) {
            user.setPassword(resetPasswordDto.getPassword());
            return true;
        }
        throw new UserNotFoundException();
    }

    @Override
    public boolean blockUser(UserActionRequestDto userActionRequestDto){
        User currentUser = userActionRequestDto.getCurrentUser();
        String email = userActionRequestDto.getEmailToAction();
        User userToBlock = userRepository.findByEmail(email);

        if(Objects.isNull(userToBlock)){
            throw new UserNotFoundException();
        }

        if (currentUser.getRole() == User.Role.ADMIN) {
            userToBlock.setBlocked(true);
            return true;
        } else {
            throw new NoPermissionsException();
        }
    }

    @Override
    public boolean unblockUser(UserActionRequestDto userActionRequestDto) {
        User currentUser = userActionRequestDto.getCurrentUser();
        String email = userActionRequestDto.getEmailToAction();
        User userToUnblock = userRepository.findByEmail(email);

        if(Objects.isNull(userToUnblock)){
            throw new UserNotFoundException();
        }

        if (currentUser.getRole() == User.Role.ADMIN) {
            userToUnblock.setBlocked(false);
            return true;
        } else {
            throw new NoPermissionsException();
        }
    }

    @Override
    public List<User> getAllUsers(User currentUser) {
        if (currentUser.getRole() != User.Role.ADMIN) {
            throw new NoPermissionsException();
        }
        return userRepository.findAll();
    }
    /**
     * Проверяет, является ли переданный email-адрес валидным.
     *
     * @param email Email-адрес, который необходимо проверить на валидность.
     * @return Возвращает {@code true}, если email-адрес валиден.
     * @throws NotValidEmailException Выбрасывается, если переданный email-адрес не соответствует формату.
     */
    private boolean isValidEmail(String email) {
        if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
            throw new NotValidEmailException();
        }
        return true;
    }
}
