package homework1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для обновления информации о пользователе.
 */
@Data
@AllArgsConstructor
@Builder
public class UpdateUserDto {

    /**
     * Email пользователя, информацию о котором нужно обновить.
     */
    private String email;

    /**
     * Новое имя пользователя.
     */
    private String newUsername;

    /**
     * Новый email пользователя.
     */
    private String newEmail;

    /**
     * Новый пароль пользователя.
     */
    private String newPassword;
}