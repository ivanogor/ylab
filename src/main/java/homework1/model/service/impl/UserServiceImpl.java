package homework1.model.service.impl;

import homework1.model.dto.LoginDto;
import homework1.model.dto.ResetPasswordDto;
import homework1.model.dto.UpdateUserDto;
import homework1.model.dto.UserActionRequestDto;
import homework1.model.entity.User;
import homework1.model.exception.NoPermissionsException;
import homework1.model.exception.UserAlreadyExistException;
import homework1.model.exception.UserNotFoundException;
import homework1.model.repository.UserRepository;
import homework1.model.service.UserService;
import homework1.model.utils.PasswordHasher;
import homework1.model.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Реализация интерфейса {@link UserService} для управления пользователями.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean register(User user) {
        ValidationUtils.validateEmail(user.getEmail());
        hashUserPassword(user);
        if (userRepository.isExist(user.getEmail())) {
            throw new UserAlreadyExistException();
        }
        userRepository.addUser(user);
        return true;
    }

    @Override
    public User login(LoginDto loginDto) {
        User user = findUserByEmail(loginDto.getEmail());
        ValidationUtils.validatePassword(user, loginDto.getPassword());
        return user;
    }

    @Override
    public boolean update(UpdateUserDto updateUserDto) {
        User user = findUserByEmail(updateUserDto.getEmail());
        updateUserFields(user, updateUserDto);
        return true;
    }

    @Override
    public boolean delete(UserActionRequestDto userActionRequestDto) {
        User userToDelete = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkPermissions(userActionRequestDto.getCurrentUser(), userToDelete);
        return userRepository.deleteUser(userToDelete.getEmail());
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = findUserByEmail(resetPasswordDto.getEmail());
        user.setPassword(resetPasswordDto.getPassword());
        return true;
    }

    @Override
    public boolean blockUser(UserActionRequestDto userActionRequestDto) {
        User userToBlock = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkAdminPermissions(userActionRequestDto.getCurrentUser());
        userToBlock.setBlocked(true);
        return true;
    }

    @Override
    public boolean unblockUser(UserActionRequestDto userActionRequestDto) {
        User userToUnblock = findUserByEmail(userActionRequestDto.getEmailToAction());
        checkAdminPermissions(userActionRequestDto.getCurrentUser());
        userToUnblock.setBlocked(false);
        return true;
    }

    @Override
    public List<User> getAllUsers(User currentUser) {
        checkAdminPermissions(currentUser);
        return userRepository.findAll();
    }

    private void hashUserPassword(User user) {
        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
    }

    private User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        return user;
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
        if (currentUser.getRole() != User.Role.ADMIN && !currentUser.equals(targetUser)) {
            throw new NoPermissionsException();
        }
    }

    private void checkAdminPermissions(User currentUser) {
        if (currentUser.getRole() != User.Role.ADMIN) {
            throw new NoPermissionsException();
        }
    }
}