package homework1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для сброса пароля пользователя.
 */
@Data
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

    /**
     * Email пользователя, для которого нужно сбросить пароль.
     */
    private String email;

    /**
     * Новый пароль пользователя.
     */
    private String password;
}