package homework1.dto;

import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для генерации отчета о прогрессе пользователя.
 */
@Data
@AllArgsConstructor
@Builder
public class GenerateUserProgressReportDto {

    /**
     * Пользователь, для которого генерируется отчет о прогрессе.
     */
    private User user;

    /**
     * Дата начала периода, за который генерируется отчет.
     */
    private LocalDate startDate;

    /**
     * Дата окончания периода, за который генерируется отчет.
     */
    private LocalDate endDate;
}