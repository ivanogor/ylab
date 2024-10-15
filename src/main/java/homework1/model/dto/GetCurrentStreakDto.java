package homework1.model.dto;

import homework1.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для получения текущей серии выполненных привычек.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
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