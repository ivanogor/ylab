package homework3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий роль пользователя в системе.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Role {

    /**
     * Уникальный идентификатор роли.
     */
    private Long id;

    /**
     * Название роли.
     */
    private String name;
}