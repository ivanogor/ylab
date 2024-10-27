package homework3.dto;

import homework3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) для генерации статистики по привычкам.
 */
@Data
@AllArgsConstructor
@Builder
public class GenerateHabitStatisticsDto {

    /**
     * Пользователь, чья статистика генерируется.
     */
    @NotNull(message = "User cannot be null")
    private User user;

    /**
     * Имя привычки, для которой генерируется статистика.
     */
    @NotBlank(message = "Habit name cannot be blank")
    private String habitName;

    /**
     * Период, за который генерируется статистика.
     */
    @NotNull(message = "Period cannot be null")
    private Period period;

    /**
     * Перечисление возможных периодов для генерации статистики.
     */
    public enum Period {
        DAY, WEEK, MONTH
    }
}