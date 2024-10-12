package homework1.dto;

import homework1.entity.Habit;
import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) для обновления информации о привычке.
 * Этот DTO используется для передачи данных, необходимых для обновления привычки,
 * включая старое имя привычки, новое имя, новое описание и новую частоту выполнения.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateHabitDto {

    /**
     * Пользователь, который выполняет обновление.
     */
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