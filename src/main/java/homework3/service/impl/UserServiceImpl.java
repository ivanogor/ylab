package homework3.service.impl;

import homework3.dto.*;
import homework3.entity.Role;
import homework3.entity.User;
import homework3.exception.NoPermissionsException;
import homework3.exception.UserAlreadyExistException;
import homework3.exception.UserNotFoundException;
import homework3.exception.WrongPasswordException;
import homework3.mapper.UserMapper;
import homework3.repository.UserRepository;
import homework3.service.UserService;
import homework3.utils.PasswordHasher;
import homework3.utils.validators.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static homework3.utils.RequestUtils.getCurrentUser;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public OperationResultDto register(UserDto userDto) {
        ValidationUtils.validate(userDto);

        // Преобразуем UserDto в User с использованием UserMapper
        User user = UserMapper.INSTANCE.toEntity(userDto);

        // Устанавливаем роль по умолчанию
        user.setRole(Role.builder().id(2L).name("USER").build());

        // Хешируем пароль пользователя
        hashUserPassword(user);

        // Проверяем, существует ли пользователь с таким email
        if (userRepository.isExist(user.getEmail())) {
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exists");
        }

        // Добавляем пользователя в репозиторий
        userRepository.addUser(user);

        // Возвращаем результат операции
        return createOperationResult(true, "User registered successfully", null);
    }

    @Override
    public OperationResultDto login(LoginDto loginDto) {
        ValidationUtils.validate(loginDto);
        User user = findUserByEmail(loginDto.getEmail());
        validatePassword(user, loginDto.getPassword());

        return createOperationResult(true, "User logged in successfully", user);
    }

    @Override
    public OperationResultDto update(UpdateUserDto updateUserDto) {
        ValidationUtils.validate(updateUserDto);
        User user = findUserByEmail(updateUserDto.getEmail());
        checkPermissions(getCurrentUser(updateUserDto.getRequest()), user);
        updateUserFields(user, updateUserDto);
        return createOperationResult(true, "User updated successfully", null);
    }

    @Override
    public OperationResultDto delete(UserActionRequestDto userActionRequestDto) {
        ValidationUtils.validate(userActionRequestDto);
        User userToDelete = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkPermissions(getCurrentUser(userActionRequestDto.getRequest()), userToDelete);
        boolean deleted = userRepository.deleteUser(userToDelete.getEmail());
        return createOperationResult(deleted, deleted ? "User deleted successfully" : "Failed to delete user", null);
    }

    @Override
    public OperationResultDto resetPassword(ResetPasswordDto resetPasswordDto) {
        ValidationUtils.validate(resetPasswordDto);
        User user = findUserByEmail(resetPasswordDto.getEmail());
        checkPermissions(getCurrentUser(resetPasswordDto.getRequest()), user);
        user.setPassword(resetPasswordDto.getPassword());
        return createOperationResult(true, "Password reset successfully", null);
    }

    @Override
    public OperationResultDto blockUser(UserActionRequestDto userActionRequestDto) {
        ValidationUtils.validate(userActionRequestDto);
        User userToBlock = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkAdminPermissions(getCurrentUser(userActionRequestDto.getRequest()));
        userToBlock.setBlocked(true);
        return createOperationResult(true, "User blocked successfully", null);
    }

    @Override
    public OperationResultDto unblockUser(UserActionRequestDto userActionRequestDto) {
        ValidationUtils.validate(userActionRequestDto);
        User userToUnblock = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkAdminPermissions(getCurrentUser(userActionRequestDto.getRequest()));
        userToUnblock.setBlocked(false);
        return createOperationResult(true, "User unblocked successfully", null);
    }

    @Override
    public OperationResultDto getAllUsers(HttpServletRequest req) {
        checkAdminPermissions(getCurrentUser(req));
        List<User> users = userRepository.findAll();
        return createOperationResult(true, "Users retrieved successfully", users);
    }

    private void hashUserPassword(User user) {
        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    private void validatePassword(User user, String password) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        if (!hashedPassword.equals(user.getPassword())) {
            throw new WrongPasswordException("Wrong password for user with email " + user.getEmail());
        }
    }

    private void updateUserFields(User user, UpdateUserDto updateUserDto) {
        if (Objects.nonNull(updateUserDto.getNewName())) {
            user.setName(updateUserDto.getNewName());
        }
        if (Objects.nonNull(updateUserDto.getNewEmail())) {
            user.setEmail(updateUserDto.getNewEmail());
        }
        if (Objects.nonNull(updateUserDto.getNewPassword())) {
            user.setPassword(updateUserDto.getNewPassword());
        }
    }

    private void checkPermissions(User currentUser, User targetUser) {
        if (currentUser.getRole().getName().equals("ADMIN") && !currentUser.equals(targetUser)) {
            throw new NoPermissionsException("User " + currentUser.getEmail() + " has no permissions to perform this action on user " + targetUser.getEmail());
        }
    }

    private void checkAdminPermissions(User currentUser) {
        if (!currentUser.getRole().getName().equals("ADMIN")) {
            throw new NoPermissionsException("User " + currentUser.getEmail() + " has no admin permissions to perform this action");
        }
    }

    private OperationResultDto createOperationResult(boolean success, String message, Object data) {
        return OperationResultDto.builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}