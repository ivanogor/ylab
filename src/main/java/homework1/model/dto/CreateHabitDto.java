package homework1.model.dto;

import homework1.model.entity.Habit;
import homework1.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для создания новой привычки.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
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