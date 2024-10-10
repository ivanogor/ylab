package homework1.repository;

import homework1.entity.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Репозиторий для хранения и управления пользователями.
 * Используется для сохранения, обновления и получения данных о пользователях.
 */
@Getter
public class UserRepository {

    /**
     * Карта для хранения пользователей, где ключом является email пользователя.
     */
    private final Map<String, User> users = new HashMap<>();
}