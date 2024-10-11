package service;

import homework1.dto.LoginDto;
import homework1.dto.ResetPasswordDto;
import homework1.dto.UpdateUserDto;
import homework1.dto.UserActionRequestDto;
import homework1.entity.User;
import homework1.exception.*;
import homework1.repository.UserRepository;
import homework1.service.impl.UserServiceImpl;
import homework1.utils.PasswordHasher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.UserUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Register user functionality")
    public void givenUser_whenRegister_thenUserIsRegistered() {
        //given
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.isExist(anyString())).thenReturn(false);
        boolean result = userService.register(user);
        //then
        assertTrue(result);
        verify(userRepository).addUser(user);
    }

    @Test
    @DisplayName("Register existed user functionality")
    public void givenExistedUser_whenRegister_thenExceptionIsThrown() {
        //given
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.isExist(anyString())).thenReturn(true);
        //then
        assertThrows(UserAlreadyExistException.class, () -> userService.register(user));
        verify(userRepository, never()).addUser(user);
    }

    @Test
    @DisplayName("Register existed user functionality")
    public void givenUserWithInvalidEmail_whenRegister_thenExceptionIsThrown() {
        //given
        User user = UserUtils.getFirstUser();
        user.setEmail("invalid.com");
        //when
        when(userRepository.isExist(anyString())).thenReturn(false);
        //then
        assertThrows(NotValidEmailException.class, () -> userService.register(user));
        verify(userRepository, never()).addUser(user);
    }

    @Test
    @DisplayName("Login user dont exist functionality")
    public void givenLoginDto_whenLogin_thenExceptionIsThrown() {
        //given
        LoginDto loginDto = UserUtils.getLoginDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.login(loginDto));
    }

    @Test
    @DisplayName("Login user functionality")
    public void givenLoginDto_whenLogin_thenLoggedUserIsReturned() {
        //given
        LoginDto loginDto = UserUtils.getLoginDto();
        User user = UserUtils.getFirstUser();
        user.setPassword(PasswordHasher.hashPassword(user.getPassword()));
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        //then
        User expected = userService.login(loginDto);
        assertThat(expected).isNotNull();
    }

    @Test
    @DisplayName("Login user with wrong password functionality")
    public void givenLoginDto_whenLogin_thenWrongPasswordExceptionIsReturned() {
        //given
        LoginDto loginDto = UserUtils.getLoginDto();
        User user = UserUtils.getFirstUser();
        user.setPassword(PasswordHasher.hashPassword("wrongPassword"));
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        //then
        assertThrows(WrongPasswordException.class, () -> userService.login(loginDto));
    }

    @Test
    @DisplayName("Update user functionality")
    public void givenUpdateUserDto_whenUpdate_thenUserIsUpdated() {
        //given
        UpdateUserDto updateUserDto = UserUtils.getUpdateUserDto();
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        boolean result = userService.update(updateUserDto);
        //then
        assertTrue(result);
        assertThat(user.getName()).isEqualTo(updateUserDto.getNewName());
        assertThat(user.getEmail()).isEqualTo(updateUserDto.getNewEmail());
        assertThat(user.getPassword()).isEqualTo(updateUserDto.getNewPassword());
    }

    @Test
    @DisplayName("Update invalid user functionality")
    public void givenUpdateUserDto_whenUserNotFound_thenExceptionIsThrown() {
        //given
        UpdateUserDto updateUserDto = UserUtils.getUpdateUserDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.update(updateUserDto));
    }

    @Test
    @DisplayName("Delete user by himself functionality")
    public void givenUserActionRequestDto_whenUserDeleteHimSelf_thenUserIsDeleted() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithUserRoleActionWhichDeleteHimselfRequestDto();
        User user = userActionRequestDto.getCurrentUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.deleteUser(anyString())).thenReturn(true);
        boolean result = userService.delete(userActionRequestDto);
        //then
        assertTrue(result);
        verify(userRepository).deleteUser(anyString());
    }

    @Test
    @DisplayName("Delete user by admin functionality")
    public void givenUserActionRequestDto_whenAdminDeleteAnotherUser_thenUserIsDeleted() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        User userToDelete = UserUtils.getFirstUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(userToDelete);
        when(userRepository.deleteUser(anyString())).thenReturn(true);
        boolean result = userService.delete(userActionRequestDto);
        //then
        assertTrue(result);
        verify(userRepository).deleteUser(anyString());
    }

    @Test
    @DisplayName("Delete invalid user functionality")
    public void givenUserActionRequestDtoWithInvalidEmail_whenDelete_thenExceptionIsThrown() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.delete(userActionRequestDto));
    }

    @Test
    @DisplayName("Delete user by another user functionality")
    public void givenUserActionRequestDto_whenUserDeleteAnotherUser_thenExceptionIsThrown() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithUserRoleWhichActionAnotherUserActionRequestDto();
        User userToDelete = UserUtils.getThirdUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(userToDelete);
        //then
        assertThrows(NoPermissionsException.class, () -> userService.delete(userActionRequestDto));
        verify(userRepository, never()).deleteUser(anyString());
    }

    @Test
    @DisplayName("Change user's password by email functionality")
    public void givenResetPasswordDto_whenResetPassword_thenPasswordIsChanged() {
        //given
        ResetPasswordDto resetPasswordDto = UserUtils.getResetPasswordDto();
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        boolean result = userService.resetPassword(resetPasswordDto);
        //then
        assertTrue(result);
        assertThat(user.getPassword()).isEqualTo(resetPasswordDto.getPassword());
    }

    @Test
    @DisplayName("Change user's password by invalid email functionality")
    public void givenResetPasswordDtoWithInvalidEmail_whenResetPassword_thenExceptionIsThrown() {
        //given
        ResetPasswordDto resetPasswordDto = UserUtils.getResetPasswordDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.resetPassword(resetPasswordDto));
    }

    @Test
    @DisplayName("Block user by admin functionality")
    public void givenUserActionRequestDto_whenAdminBlockUser_thenUserIsBlocked() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        User userToBlock = UserUtils.getFirstUser();
        assertThat(userToBlock.isBlocked()).isEqualTo(false);
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(userToBlock);
        boolean result = userService.blockUser(userActionRequestDto);
        //then
        assertTrue(result);
        assertTrue(userToBlock.isBlocked());
    }

    @Test
    @DisplayName("Block user with invalid email by admin functionality")
    public void givenUserActionRequestDtoWithInvalidEmail_whenBlockUser_thenExceptionIsReturned() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.blockUser(userActionRequestDto));
    }

    @Test
    @DisplayName("Block user by user functionality")
    public void givenUserActionRequestDto_whenUserBlockUser_thenExceptionIsReturned() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithUserRoleWhichActionAnotherUserActionRequestDto();
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        //then
        assertThrows(NoPermissionsException.class, () -> userService.blockUser(userActionRequestDto));
    }

    @Test
    @DisplayName("Unblock user by admin functionality")
    public void givenUserActionRequestDto_whenAdminUnblockUser_thenUserIsBlocked() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        User userToBlock = UserUtils.getFirstUser();
        assertThat(userToBlock.isBlocked()).isEqualTo(false);
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(userToBlock);
        boolean result = userService.unblockUser(userActionRequestDto);
        //then
        assertTrue(result);
        assertFalse(userToBlock.isBlocked());
    }

    @Test
    @DisplayName("Unblock user with invalid email by admin functionality")
    public void givenUserActionRequestDtoWithInvalidEmail_whenUnblockUser_thenExceptionIsReturned() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithAdminRoleActionRequestDto();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.unblockUser(userActionRequestDto));
    }

    @Test
    @DisplayName("Unblock user by user functionality")
    public void givenUserActionRequestDto_whenUserUnblockUser_thenExceptionIsReturned() {
        //given
        UserActionRequestDto userActionRequestDto = UserUtils.getUserWithUserRoleWhichActionAnotherUserActionRequestDto();
        User user = UserUtils.getFirstUser();
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        //then
        assertThrows(NoPermissionsException.class, () -> userService.unblockUser(userActionRequestDto));
    }
}
