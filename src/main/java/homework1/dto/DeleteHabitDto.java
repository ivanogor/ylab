package homework1.dto;

import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для удаления привычки.
 */
@Data
@AllArgsConstructor
@Builder
public class DeleteHabitDto {

    /**
     * Пользователь, чья привычка удаляется.
     */
    private User user;

    /**
     * Имя привычки, которую нужно удалить.
     */
    private String name;
}