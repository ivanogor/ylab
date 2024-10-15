package homework1.model.dto;

import homework1.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
    private User user;

    /**
     * Имя привычки, для которой генерируется статистика.
     */
    private String habitName;

    /**
     * Период, за который генерируется статистика.
     */
    private Period period;

    /**
     * Перечисление возможных периодов для генерации статистики.
     */
    public enum Period {
        DAY, WEEK, MONTH
    }
}