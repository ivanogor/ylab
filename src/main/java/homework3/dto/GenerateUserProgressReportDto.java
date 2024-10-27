package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для генерации отчета о прогрессе пользователя.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GenerateUserProgressReportDto {

    /**
     * Дата начала периода, за который генерируется отчет.
     */
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    /**
     * Дата окончания периода, за который генерируется отчет.
     */
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    private HttpServletRequest request;
}