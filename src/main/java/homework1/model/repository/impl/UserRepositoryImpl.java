package homework1.model.repository.impl;

import homework1.model.entity.User;
import homework1.model.exception.UserNotFoundException;
import homework1.model.repository.UserRepository;
import homework1.model.utils.DatabaseConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO habit_tracker.users (name, email, password, role, is_blocked) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole().name());
            pstmt.setBoolean(5, user.isBlocked());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            }
            log.info("User added: {}", user);
            return true;
        } catch (SQLException e) {
            log.error("Failed to add user", e);
            return false;
        }
    }

    @Override
    public boolean isExist(String email) {
        String sql = "SELECT COUNT(*) FROM habit_tracker.users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boolean exists = rs.getInt(1) > 0;
                    log.info("User with email {} exists: {}", email, exists);
                    return exists;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to check if user exists", e);
        }
        return false;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM habit_tracker.users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .role(User.Role.valueOf(rs.getString("role")))
                            .isBlocked(rs.getBoolean("is_blocked"))
                            .build();
                    log.info("User found by email {}: {}", email, user);
                    return user;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find user by email", e);
        }
        log.warn("User not found by email: {}", email);
        throw new UserNotFoundException();
    }

    @Override
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM habit_tracker.users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("User deleted by email: {}", email);
            } else {
                log.warn("User not found for deletion by email: {}", email);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to delete user", e);
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM habit_tracker.users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .role(User.Role.valueOf(rs.getString("role")))
                        .isBlocked(rs.getBoolean("is_blocked"))
                        .build();
                users.add(user);
            }
            log.info("All users found: {}", users);
        } catch (SQLException e) {
            log.error("Failed to find all users", e);
        }
        return users;
    }
}