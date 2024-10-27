package homework3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) для передачи данных аутентификации пользователя.
 * Этот DTO используется для передачи email и пароля пользователя при входе в систему.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginDto {
    /**
     * Email пользователя.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Пароль пользователя.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}