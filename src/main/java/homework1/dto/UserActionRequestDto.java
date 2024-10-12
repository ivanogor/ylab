package homework1.dto;

import homework1.entity.User;
import lombok.*;

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
     * Текущий пользователь, выполняющий действие.
     */
    private User currentUser;

    /**
     * Email пользователя, над которым выполняется действие.
     */
    private String emailToAction;
}