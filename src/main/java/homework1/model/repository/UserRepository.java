package homework1.model.repository;

import homework1.model.entity.User;
import homework1.model.exception.UserNotFoundException;

import java.util.List;

/**
 * Интерфейс репозитория для управления пользователями.
 * Предоставляет методы для добавления, поиска, удаления и получения списка пользователей.
 */
public interface UserRepository {

    /**
     * Добавляет нового пользователя.
     *
     * @param user Пользователь, которого нужно добавить.
     */
    boolean addUser(User user);

    /**
     * Проверяет, существует ли пользователь с указанным email.
     *
     * @param email Email пользователя для проверки.
     * @return true, если пользователь существует, иначе false.
     */
    boolean isExist(String email);

    /**
     * Находит пользователя по его email.
     *
     * @param email Email пользователя для поиска.
     * @return Пользователь с указанным email.
     * @throws UserNotFoundException если пользователь не найден.
     */
    User findByEmail(String email);

    /**
     * Удаляет пользователя по его email.
     *
     * @param email Email пользователя для удаления.
     * @return true, если пользователь был удален, иначе false.
     * @throws UserNotFoundException если пользователь не найден.
     */
    boolean deleteUser(String email);

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     */
    List<User> findAll();
}