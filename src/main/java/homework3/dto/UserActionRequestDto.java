package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) для запроса действия над пользователем.
 * Этот DTO используется для передачи данных, необходимых для выполнения действия
 * (например, блокировки или удаления) над другим пользователем.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserActionRequestDto {
    /**
     * Email пользователя, над которым выполняется действие.
     */
    @NotBlank(message = "Email to action cannot be blank")
    @Email(message = "Email to action should be valid")
    private String emailToAction;

    private HttpServletRequest request;
}