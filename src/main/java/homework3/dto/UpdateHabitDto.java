package homework3.dto;

import homework3.entity.Habit;
import homework3.utils.validators.AtLeastOneNotEmpty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
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
@AtLeastOneNotEmpty(message = "At least one of the new fields must be not empty")
public class UpdateHabitDto {
    /**
     * Старое имя привычки, которую нужно обновить.
     */
    @NotBlank(message = "Old habit name cannot be blank")
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

    private HttpServletRequest request;
}