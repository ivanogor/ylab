package homework3.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) для сброса пароля пользователя.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResetPasswordDto {

    /**
     * Email пользователя, для которого нужно сбросить пароль.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Новый пароль пользователя.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private HttpServletRequest request;
}