package homework3.dto;

import homework3.entity.Habit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для получения привычек пользователя по частоте выполнения.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetHabitsByFrequencyDateDto {

    /**
     * Частота выполнения привычек, по которой производится фильтрация.
     */
    @NotNull(message = "Frequency cannot be null")
    private Habit.Frequency frequency;

    private HttpServletRequest request;
}