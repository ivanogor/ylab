package homework1.model.repository;

import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;

import java.util.Set;

/**
 * Интерфейс репозитория для управления привычками.
 */
public interface HabitRepository {

    /**
     * Создает новую привычку для пользователя.
     *
     * @param user  Пользователь, для которого создается привычка.
     * @param habit Привычка, которую нужно создать.
     * @return true, если привычка успешно создана, иначе false.
     */
    Habit createHabit(User user, Habit habit);

    /**
     * Обновляет существующую привычку пользователя.
     *
     * @param user  Пользователь, чья привычка обновляется.
     * @param habit Привычка, которую нужно обновить.
     * @return true, если привычка успешно обновлена, иначе false.
     */
    boolean updateHabit(User user, Habit habit);

    /**
     * Удаляет привычку пользователя по имени.
     *
     * @param user Пользователь, чья привычка удаляется.
     * @param name Имя привычки, которую нужно удалить.
     * @return true, если привычка успешно удалена, иначе false.
     */
    boolean deleteHabit(User user, String name);

    /**
     * Возвращает все привычки пользователя.
     *
     * @param user Пользователь, чьи привычки нужно получить.
     * @return Множество привычек пользователя.
     */
    Set<Habit> getAllHabits(User user);

    /**
     * Находит привычку пользователя по имени.
     *
     * @param user Пользователь, чья привычка ищется.
     * @param name Имя привычки, которую нужно найти.
     * @return Привычка с указанным именем.
     * @throws HabitNotFoundException если привычка с указанным именем не найдена.
     */
    Habit getHabitByName(User user, String name);
}