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
 * DTO (Data Transfer Object) для получения процента выполнения привычек.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetCompletionPercentageDto {

    /**
     * Имя привычки, для которой вычисляется процент выполнения.
     */
    @NotBlank(message = "Habit name cannot be blank")
    private String name;

    /**
     * Дата начала периода, за который вычисляется процент выполнения.
     */
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    /**
     * Дата окончания периода, за который вычисляется процент выполнения.
     */
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    private HttpServletRequest request;
}