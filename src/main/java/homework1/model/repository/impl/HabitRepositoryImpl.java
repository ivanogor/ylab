package homework1.model.repository.impl;

import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.utils.CompletionSerializer;
import homework1.model.utils.DatabaseConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class HabitRepositoryImpl implements HabitRepository {

    @Override
    public Habit createHabit(User user, Habit habit) {
        String sql = "INSERT INTO habit_tracker.habits (name, description, frequency, creation_date, completions, is_completed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, habit.getName());
            pstmt.setString(2, habit.getDescription());
            pstmt.setString(3, habit.getFrequency().name());
            pstmt.setDate(4, Date.valueOf(habit.getCreationDate()));
            pstmt.setString(5, CompletionSerializer.serializeCompletions(habit.getCompletions()));
            pstmt.setBoolean(6, habit.isCompleted());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long habitId = rs.getLong(1);
                        habit.setId(habitId);
                        addHabitToUser(user, habitId);
                    }
                }
            }
            log.info("Habit created: {}", habit);
            return habit;
        } catch (SQLException e) {
            log.error("Failed to create habit", e);
            return null;
        }
    }

    private void addHabitToUser(User user, long habitId) {
        String sql = "INSERT INTO habit_tracker.user_habits (user_id, habit_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            pstmt.setLong(2, habitId);
            pstmt.executeUpdate();
            log.info("Habit added to user: user_id={}, habit_id={}", user.getId(), habitId);
        } catch (SQLException e) {
            log.error("Failed to add habit to user", e);
        }
    }

    @Override
    public boolean updateHabit(User user, Habit habit) {
        String sql = "UPDATE habit_tracker.habits SET name = ?, description = ?, frequency = ?, creation_date = ?, completions = ?, is_completed = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, habit.getName());
            pstmt.setString(2, habit.getDescription());
            pstmt.setString(3, habit.getFrequency().name());
            pstmt.setDate(4, Date.valueOf(habit.getCreationDate()));
            pstmt.setString(5, CompletionSerializer.serializeCompletions(habit.getCompletions()));
            pstmt.setBoolean(6, habit.isCompleted());
            pstmt.setLong(7, habit.getId());
            int affectedRows = pstmt.executeUpdate();
            log.info("Habit updated: {}", habit);
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to update habit", e);
            return false;
        }
    }

    @Override
    public boolean deleteHabit(User user, String name) {
        String sql = "DELETE FROM habit_tracker.habits WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Habit deleted: name={}", name);
            } else {
                log.warn("Habit not found for deletion: name={}", name);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to delete habit", e);
            return false;
        }
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        String sql = "SELECT h.* FROM habit_tracker.habits h JOIN habit_tracker.user_habits uh ON h.id = uh.habit_id WHERE uh.user_id = ?";
        Set<Habit> habits = new HashSet<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Habit habit = Habit.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .frequency(Habit.Frequency.valueOf(rs.getString("frequency")))
                            .creationDate(rs.getDate("creation_date").toLocalDate())
                            .completions(CompletionSerializer.deserializeCompletions(rs.getString("completions")))
                            .isCompleted(rs.getBoolean("is_completed"))
                            .build();
                    habits.add(habit);
                }
            }
            log.info("All habits found for user: user_id={}, habits={}", user.getId(), habits);
        } catch (SQLException e) {
            log.error("Failed to get all habits for user", e);
        }
        return habits;
    }

    @Override
    public Habit getHabitByName(User user, String name) {
        String sql = "SELECT h.* FROM habit_tracker.habits h JOIN habit_tracker.user_habits uh ON h.id = uh.habit_id WHERE uh.user_id = ? AND h.name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, user.getId());
            pstmt.setString(2, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Habit habit = Habit.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .frequency(Habit.Frequency.valueOf(rs.getString("frequency")))
                            .creationDate(rs.getDate("creation_date").toLocalDate())
                            .completions(CompletionSerializer.deserializeCompletions(rs.getString("completions")))
                            .isCompleted(rs.getBoolean("is_completed"))
                            .build();
                    log.info("Habit found by name: user_id={}, habit={}", user.getId(), habit);
                    return habit;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get habit by name", e);
        }
        log.warn("Habit not found by name: user_id={}, name={}", user.getId(), name);
        throw new HabitNotFoundException();
    }
}