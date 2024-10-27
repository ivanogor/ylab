package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для получения текущей серии выполненных привычек.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetCurrentStreakDto {

    /**
     * Имя привычки, для которой вычисляется текущая серия.
     */
    @NotBlank(message = "Habit name cannot be blank")
    private String name;

    private HttpServletRequest request;
}