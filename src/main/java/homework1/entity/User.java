package homework1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Класс, представляющий пользователя системы.
 */
@Data
@AllArgsConstructor
@Builder
public class User {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Email пользователя.
     */
    private String email;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Список привычек пользователя.
     */
    private List<Habit> habits;
}