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

    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public boolean isExist(String email) {
        return users.containsKey(email);
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public boolean deleteUser(String email) {
        if(isExist(email)){
            users.remove(email);
            return true;
        }
        throw new UserNotFoundException();
    }

    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}