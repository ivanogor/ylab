package homework1.model.repository.impl;

import homework1.model.entity.User;
import homework1.model.exception.UserNotFoundException;
import homework1.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация интерфейса {@link UserRepository} для управления пользователями.
 * Используется для сохранения, обновления и получения данных о пользователях.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    /**
     * Карта для хранения пользователей, где ключом является email пользователя.
     */
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public boolean isExist(String email) {
        return users.containsKey(email);
    }

    @Override
    public User findByEmail(String email) {
        User user = users.get(email);
        if(Objects.isNull(user)){
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public boolean deleteUser(String email) {
        if (isExist(email)) {
            users.remove(email);
            return true;
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}