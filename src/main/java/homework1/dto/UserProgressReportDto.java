package homework1.dto;

import homework1.entity.Habit;
import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    /**
     * Список прогрессов по привычкам пользователя.
     */
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