package homework1.dto;

import homework1.entity.Habit;
import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для создания новой привычки.
 */
@Data
@AllArgsConstructor
@Builder
public class CreateHabitDto {

    /**
     * Пользователь, для которого создается привычка.
     */
    private User user;

    /**
     * Привычка, которую нужно создать.
     */
    private Habit habit;
}