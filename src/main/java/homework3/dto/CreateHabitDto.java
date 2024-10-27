package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) для создания привычки.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateHabitDto {
    /**
     * Привычка, которую нужно создать.
     */
    @NotNull(message = "Habit cannot be null")
    private HabitDto habitDto;

    private HttpServletRequest request;
}