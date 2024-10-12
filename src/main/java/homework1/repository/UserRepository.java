package homework1.repository;

import homework1.entity.User;
import homework1.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Репозиторий для хранения и управления пользователями.
 * Используется для сохранения, обновления и получения данных о пользователях.
 */
public class UserRepository {

    /**
     * Карта для хранения пользователей, где ключом является email пользователя.
     */
    private final Map<String, User> users = new HashMap<>();

    /**
     * Добавляет пользователя в репозиторий.
     *
     * @param user Пользователь, которого нужно добавить.
     */
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    /**
     * Проверяет, существует ли пользователь с указанным email.
     *
     * @param email Email пользователя для проверки.
     * @return true, если пользователь существует, иначе false.
     */
    public boolean isExist(String email) {
        return users.containsKey(email);
    }

    /**
     * Находит пользователя по его email.
     *
     * @param email Email пользователя для поиска.
     * @return Пользователь с указанным email.
     * @throws UserNotFoundException если пользователь не найден.
     */
    public User findByEmail(String email) {
        return users.get(email);
    }

    /**
     * Удаляет пользователя по его email.
     *
     * @param email Email пользователя для удаления.
     * @return true, если пользователь был удален, иначе false.
     * @throws UserNotFoundException если пользователь не найден.
     */
    public boolean deleteUser(String email) {
        if(isExist(email)){
            users.remove(email);
            return true;
        }
        throw new UserNotFoundException();
    }

    /**
     * Возвращает список всех пользователей в репозитории.
     *
     * @return Список всех пользователей.
     */
    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}