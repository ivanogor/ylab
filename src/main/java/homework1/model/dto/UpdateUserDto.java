package homework1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для обновления информации о пользователе.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateUserDto {

    /**
     * Email пользователя, информацию о котором нужно обновить.
     */
    private String email;

    /**
     * Новое имя пользователя.
     */
    private String newName;

    /**
     * Новый email пользователя.
     */
    private String newEmail;

    /**
     * Новый пароль пользователя.
     */
    private String newPassword;
}