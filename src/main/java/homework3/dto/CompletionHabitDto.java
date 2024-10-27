package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для отметки привычки как выполненной.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CompletionHabitDto {
    /**
     * Имя привычки, которую нужно отметить как выполненную.
     */
    @NotBlank(message = "Habit name cannot be blank")
    private String habitName;

    /**
     * Дата выполнения привычки.
     */
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    private HttpServletRequest request;
}