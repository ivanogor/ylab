package homework1.dto;

import homework1.entity.User;
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
     * Пользователь, для которого вычисляется процент выполнения привычек.
     */
    private User user;

    /**
     * Имя привычки, для которой вычисляется процент выполнения.
     */
    private String name;

    /**
     * Дата начала периода, за который вычисляется процент выполнения.
     */
    private LocalDate startDate;

    /**
     * Дата окончания периода, за который вычисляется процент выполнения.
     */
    private LocalDate endDate;
}