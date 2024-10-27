package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для генерации статистики по привычкам.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CountHabitCompletionsForPeriodDto {

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

    private HttpServletRequest request;
    /**
     * Перечисление возможных периодов для генерации статистики.
     */
    public enum Period {
        DAY, WEEK, MONTH
    }
}