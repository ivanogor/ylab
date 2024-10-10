package homework1.service.impl;

import homework1.dto.ResetPasswordDto;
import homework1.dto.UpdateUserDto;
import homework1.entity.User;
import homework1.exception.NotValidEmailException;
import homework1.exception.UserAlreadyExistException;
import homework1.exception.UserNotFoundException;
import homework1.exception.WrongPasswordException;
import homework1.repository.UserRepository;
import homework1.service.UserService;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Сервис для управления пользователями.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(User user) {
        if (userRepository.getUsers().containsKey(user.getEmail())){
            throw new UserAlreadyExistException();
        }
        if(isValidEmail(user.getEmail())){
            userRepository.getUsers().put(user.getEmail(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean login(User user){
        if (userRepository.getUsers().containsKey(user.getEmail())){
            if(userRepository.getUsers().get(user.getEmail()).getPassword().equals(user.getPassword())){
                return true;
            }
            throw new WrongPasswordException();
        }
        throw new UserNotFoundException();
    }

    @Override
    public boolean update(UpdateUserDto updateUserDto){
        User user = userRepository.getUsers().get(updateUserDto.getEmail());
        if (Objects.nonNull(user)){

            if(Objects.nonNull(updateUserDto.getNewUsername())){
                user.setName(updateUserDto.getNewUsername());
            }
            if(Objects.nonNull(updateUserDto.getNewEmail())){
                user.setEmail(updateUserDto.getNewEmail());
            }
            if(Objects.nonNull(updateUserDto.getNewPassword())){
                user.setPassword(updateUserDto.getNewPassword());
            }

            return true;
        }
        throw new UserNotFoundException();
    }

    @Override
    public boolean delete(String email){
        return Objects.nonNull(userRepository.getUsers().remove(email));
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto){
        User user = userRepository.getUsers().get(resetPasswordDto.getEmail());
        if(Objects.nonNull(user)){
            user.setPassword(resetPasswordDto.getPassword());
            return true;
        }
        throw new UserNotFoundException();
    }

    /**
     * Регулярное выражение для проверки валидности email-адресов.
     */
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

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
