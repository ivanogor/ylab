package homework3.dto;

import homework3.entity.Habit;
import homework3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO (Data Transfer Object) для представления отчета о прогрессе пользователя.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressReportDto {

    /**
     * Пользователь, для которого генерируется отчет о прогрессе.
     */
    @NotNull(message = "User cannot be null")
    private User user;

    /**
     * Список прогрессов по привычкам пользователя.
     */
    @Valid
    @NotNull(message = "Habit progresses cannot be null")
    private List<HabitProgress> habitProgresses;

    /**
     * Вложенный класс для представления прогресса по отдельной привычке.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HabitProgress {

        /**
         * Привычка, по которой предоставляется информация о прогрессе.
         */
        @NotNull(message = "Habit cannot be null")
        private Habit habit;

        /**
         * Текущая серия выполненных привычек.
         */
        private int streak;

        /**
         * Процент выполнения привычки за определенный период.
         */
        private double completionPercentage;
    }
}