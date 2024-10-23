package homework1.model.utils;

import homework1.model.dto.*;
import homework1.model.entity.User;
import homework1.model.exception.NotValidEmailException;
import homework1.model.exception.WrongPasswordException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * Утилитный класс для валидации различных данных.
 */
@Slf4j
@UtilityClass
public class ValidationUtils {

    /**
     * Регулярное выражение для проверки валидности email-адресов.
     */
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

    public static void validateEmail(String email) {
        if(!EMAIL_REGEX.matcher(email).matches()){
           throw new NotValidEmailException();
        }
    }

    public static void validateCreateHabitDto(CreateHabitDto createHabitDto) {
        if (createHabitDto == null || createHabitDto.getUser() == null || createHabitDto.getHabit() == null) {
            log.error("Invalid input data for creating habit: {}", createHabitDto);
            throw new IllegalArgumentException("Invalid input data for creating habit");
        }
    }

    public static void validateUpdateHabitDto(UpdateHabitDto updateHabitDto) {
        if (updateHabitDto == null || updateHabitDto.getUser() == null || updateHabitDto.getOldHabitName() == null) {
            log.error("Invalid input data for updating habit: {}", updateHabitDto);
            throw new IllegalArgumentException("Invalid input data for updating habit");
        }
    }

    public static void validateDeleteHabitDto(DeleteHabitDto deleteHabitDto) {
        if (deleteHabitDto == null || deleteHabitDto.getUser() == null || deleteHabitDto.getName() == null) {
            log.error("Invalid input data for deleting habit: {}", deleteHabitDto);
            throw new IllegalArgumentException("Invalid input data for deleting habit");
        }
    }

    public static void validateUser(User user) {
        if (user == null) {
            log.error("Invalid input data for getting all habits: user is null");
            throw new IllegalArgumentException("User is null");
        }
    }

    public static void validateGetHabitsByCreationDateDto(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        if (getHabitsByCreationDateDto == null || getHabitsByCreationDateDto.getUser() == null || getHabitsByCreationDateDto.getCreationDate() == null) {
            log.error("Invalid input data for getting habits by creation date: {}", getHabitsByCreationDateDto);
            throw new IllegalArgumentException("Invalid input data for getting habits by creation date");
        }
    }

    public static void validateGetHabitsByFrequencyDateDto(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        if (getHabitsByFrequencyDateDto == null || getHabitsByFrequencyDateDto.getUser() == null || getHabitsByFrequencyDateDto.getFrequency() == null) {
            log.error("Invalid input data for getting habits by frequency: {}", getHabitsByFrequencyDateDto);
            throw new IllegalArgumentException("Invalid input data for getting habits by frequency");
        }
    }

    public static void validateCompletionHabitDto(CompletionHabitDto completionHabitDto) {
        if (completionHabitDto == null || completionHabitDto.getUser() == null || completionHabitDto.getHabitName() == null || completionHabitDto.getDate() == null) {
            log.error("Invalid input data for marking habit as completed: {}", completionHabitDto);
            throw new IllegalArgumentException("Invalid input data for marking habit as completed");
        }
    }

    public static void validateCountHabitCompletionsForPeriodDto(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        if (countHabitCompletionsForPeriodDto == null || countHabitCompletionsForPeriodDto.getUser() == null || countHabitCompletionsForPeriodDto.getHabitName() == null || countHabitCompletionsForPeriodDto.getPeriod() == null) {
            log.error("Invalid input data for counting habit completions for period: {}", countHabitCompletionsForPeriodDto);
            throw new IllegalArgumentException("Invalid input data for counting habit completions for period");
        }
    }

    public static void validateGetCurrentStreakDto(GetCurrentStreakDto getCurrentStreakDto) {
        if (getCurrentStreakDto == null || getCurrentStreakDto.getUser() == null || getCurrentStreakDto.getName() == null) {
            log.error("Invalid input data for getting current streak: {}", getCurrentStreakDto);
            throw new IllegalArgumentException("Invalid input data for getting current streak");
        }
    }

    public static void validateGetCompletionPercentageDto(GetCompletionPercentageDto getCompletionPercentageDto) {
        if (getCompletionPercentageDto == null || getCompletionPercentageDto.getUser() == null || getCompletionPercentageDto.getName() == null || getCompletionPercentageDto.getStartDate() == null || getCompletionPercentageDto.getEndDate() == null) {
            log.error("Invalid input data for getting completion percentage: {}", getCompletionPercentageDto);
            throw new IllegalArgumentException("Invalid input data for getting completion percentage");
        }
    }

    public static void validateGenerateUserProgressReportDto(GenerateUserProgressReportDto generateUserProgressReportDto) {
        if (generateUserProgressReportDto == null || generateUserProgressReportDto.getUser() == null || generateUserProgressReportDto.getStartDate() == null || generateUserProgressReportDto.getEndDate() == null) {
            log.error("Invalid input data for generating user progress report: {}", generateUserProgressReportDto);
            throw new IllegalArgumentException("Invalid input data for generating user progress report");
        }
    }
    public static void validatePassword(User user, String password){
        String hashedPassword = PasswordHasher.hashPassword(password);
        if (!user.getPassword().equals(hashedPassword)) {
            throw new WrongPasswordException();
        }
    }
}