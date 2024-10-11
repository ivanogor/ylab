package homework1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Класс, представляющий пользователя системы.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
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
    private Set<Habit> habits;

    private Role role;

    private boolean isBlocked;

    public enum Role{
        USER, ADMIN
    }
}