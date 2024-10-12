package homework1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Класс, представляющий пользователя системы.
 * Этот класс содержит информацию о пользователе, включая его имя, email, пароль, список привычек, роль и статус блокировки.
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

    /**
     * Роль пользователя в системе.
     */
    private Role role;

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