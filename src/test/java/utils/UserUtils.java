package utils;

import homework1.model.dto.LoginDto;
import homework1.model.dto.ResetPasswordDto;
import homework1.model.dto.UpdateUserDto;
import homework1.model.dto.UserActionRequestDto;
import homework1.model.entity.User;

public class UserUtils {
    public static User getFirstUser() {
        return User.builder()
                .email("firstAddress@mail.com")
                .name("JohnDoe")
                .password("password")
                .isBlocked(false)
                .role(User.Role.USER)
                .habits(HabitUtils.getAllHabits())
                .build();
    }

    public static User getSecondUser() {
        return User.builder()
                .email("secondAddress@mail.com")
                .name("SteveJobs")
                .password("password")
                .isBlocked(false)
                .role(User.Role.ADMIN)
                .habits(HabitUtils.getAllHabits())
                .build();
    }

    public static User getThirdUser() {
        return User.builder()
                .email("thirdAddress@mail.com")
                .name("JohnDoe")
                .password("password")
                .isBlocked(false)
                .role(User.Role.USER)
                .habits(HabitUtils.getAllHabits())
                .build();
    }

    public static LoginDto getLoginDto(){
        return LoginDto.builder()
                .email("address@mail.com")
                .password("password")
                .build();
    }

    public static UpdateUserDto getUpdateUserDto(){
        return UpdateUserDto.builder()
                .email("address@mail.com")
                .newEmail("new@mail.com")
                .newName("newName")
                .newPassword("newPassword")
                .build();
    }

    public static UserActionRequestDto getUserWithUserRoleActionWhichDeleteHimselfRequestDto(){
        return UserActionRequestDto.builder()
                .currentUser(getFirstUser())
                .emailToAction(getFirstUser().getEmail())
                .build();
    }

    public static UserActionRequestDto getUserWithAdminRoleActionRequestDto(){
        return UserActionRequestDto.builder()
                .currentUser(getSecondUser())
                .emailToAction(getFirstUser().getEmail())
                .build();
    }

    public static UserActionRequestDto getUserWithUserRoleWhichActionAnotherUserActionRequestDto(){
        return UserActionRequestDto.builder()
                .currentUser(getFirstUser())
                .emailToAction(getThirdUser().getEmail())
                .build();
    }

    public static ResetPasswordDto getResetPasswordDto(){
        return ResetPasswordDto.builder()
                .email("address@mail.com")
                .password("newPassword")
                .build();
    }
}
