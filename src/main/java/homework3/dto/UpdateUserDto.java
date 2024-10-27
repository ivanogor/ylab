package homework3.dto;

import homework3.utils.validators.AtLeastOneNotEmpty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) для обновления информации о пользователе.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@AtLeastOneNotEmpty(message = "At least one of the new fields must be not empty")
public class UpdateUserDto {

    /**
     * Email пользователя, информацию о котором нужно обновить.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
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

    private HttpServletRequest request;
}