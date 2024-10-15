package homework1.model.repository.impl;

import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;

import java.util.*;

/**
 * Реализация интерфейса {@link HabitRepository} для управления привычками.
 */
public class HabitRepositoryImpl implements HabitRepository {

    private final Map<String, Set<Habit>> habitsByUserEmail = new HashMap<>();

    @Override
    public boolean createHabit(User user, Habit habit) {
        Set<Habit> habits = habitsByUserEmail.getOrDefault(user.getEmail(), new HashSet<>());
        boolean added = habits.add(habit);
        if (added) {
            habitsByUserEmail.put(user.getEmail(), habits);
        }
        return added;
    }

    @Override
    public boolean updateHabit(User user, Habit habit) {
        Set<Habit> habits = habitsByUserEmail.getOrDefault(user.getEmail(), new HashSet<>());
        return habits.stream()
                .filter(h -> h.getName().equals(habit.getName()))
                .findFirst()
                .map(h -> {
                    h.setName(habit.getName());
                    h.setDescription(habit.getDescription());
                    h.setFrequency(habit.getFrequency());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteHabit(User user, String name) {
        Set<Habit> habits = habitsByUserEmail.getOrDefault(user.getEmail(), Collections.emptySet());
        return habits.removeIf(habit -> habit.getName().equals(name));
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        return habitsByUserEmail.getOrDefault(user.getEmail(), Collections.emptySet());
    }

    @Override
    public Habit getHabitByName(User user, String name) {
        return habitsByUserEmail.getOrDefault(user.getEmail(), Collections.emptySet()).stream()
                .filter(habit -> habit.getName().equals(name))
                .findFirst()
                .orElseThrow(HabitNotFoundException::new);
    }
}