package homework1.dto;

import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для получения привычек пользователя по дате создания.
 */
@Data
@AllArgsConstructor
@Builder
public class GetHabitsByCreationDateDto {

    /**
     * Пользователь, чьи привычки фильтруются по дате создания.
     */
    private User user;

    /**
     * Дата создания привычек, по которой производится фильтрация.
     */
    private LocalDate creationDate;
}