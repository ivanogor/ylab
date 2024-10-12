package homework1.utils;

import homework1.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Утилитный класс для хранения текущего пользователя.
 * Используется для передачи информации о текущем пользователе между различными компонентами системы.
 */
@Setter
@Getter
public class UserHolder {
    /**
     * Текущий пользователь.
     */
    private User user;
}