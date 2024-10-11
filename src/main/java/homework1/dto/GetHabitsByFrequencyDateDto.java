package homework1.dto;

import homework1.entity.Habit;
import homework1.entity.User;
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
     * Пользователь, чьи привычки фильтруются по частоте выполнения.
     */
    private User user;

    /**
     * Частота выполнения привычек, по которой производится фильтрация.
     */
    private Habit.Frequency frequency;
}