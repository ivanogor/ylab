package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для получения привычек пользователя по дате создания.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetHabitsByCreationDateDto {

    /**
     * Дата создания привычек, по которой производится фильтрация.
     */
    @NotNull(message = "Creation date cannot be null")
    private LocalDate creationDate;

    private HttpServletRequest request;
}