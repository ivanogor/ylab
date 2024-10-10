package homework1.dto;

import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для получения текущей серии выполненных привычек.
 */
@Data
@AllArgsConstructor
@Builder
public class GetCurrentStreakDto {

    /**
     * Пользователь, для которого вычисляется текущая серия выполненных привычек.
     */
    private User user;

    /**
     * Имя привычки, для которой вычисляется текущая серия.
     */
    private String name;
}