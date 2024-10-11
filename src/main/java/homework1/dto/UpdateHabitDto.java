package homework1.dto;

import homework1.entity.Habit;
import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для обновления информации о привычке.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateHabitDto {

    private User user;
    /**
     * Старое имя привычки, которую нужно обновить.
     */
    private String oldHabitName;

    /**
     * Новое имя привычки.
     */
    private String newHabitName;

    /**
     * Новое описание привычки.
     */
    private String newHabitDescription;

    /**
     * Новая частота выполнения привычки.
     */
    private Habit.Frequency newFrequency;
}