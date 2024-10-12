package homework1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String email;

    /**
     * Пароль пользователя.
     */
    private String password;
}