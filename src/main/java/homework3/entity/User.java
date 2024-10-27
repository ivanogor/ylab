package homework3.entity;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

/**
 * Класс, представляющий пользователя системы.
 * Этот класс содержит информацию о пользователе, включая его имя, email, пароль, роль и статус блокировки.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    /**
     * Email пользователя.
     */
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Пароль пользователя.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 1, max = 100, message = "Password must be between 1 and 100 characters")
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

    public @NotBlank(message = "Name cannot be blank") @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email cannot be blank") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank") @Size(min = 1, max = 100, message = "Password must be between 1 and 100 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") @Size(min = 1, max = 100, message = "Password must be between 1 and 100 characters") String password) {
        this.password = password;
    }
}