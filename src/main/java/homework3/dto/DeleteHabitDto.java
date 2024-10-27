package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для удаления привычки.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeleteHabitDto {
    /**
     * Имя привычки, которую нужно удалить.
     */
    @NotBlank(message = "Habit name cannot be blank")
    private String name;

    private HttpServletRequest request;
}