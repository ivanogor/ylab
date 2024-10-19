package homework1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Set;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий пользователя системы.
 * Этот класс содержит информацию о пользователе, включая его имя, email, пароль, роль и статус блокировки.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {

    private Long id;
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
     * Роль пользователя в системе.
     */
    private Role role;

    /**
     * Множество привычек пользователя.
     */
    private Set<Habit> habits;

    /**
     * Флаг, указывающий, заблокирован ли пользователь.
     */
    private boolean isBlocked;

    /**
     * Перечисление возможных ролей пользователя.
     */
    public enum Role {
        USER, ADMIN
    }
}