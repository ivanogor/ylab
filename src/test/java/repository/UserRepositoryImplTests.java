package repository;

import homework1.model.entity.User;
import homework1.model.exception.UserNotFoundException;
import homework1.model.repository.UserRepository;
import homework1.model.repository.impl.UserRepositoryImpl;
import homework1.model.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.UserUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTests {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("ylab")
            .withUsername("username")
            .withPassword("password");

    private UserRepository userRepository;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        DatabaseConnection.setTestConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE SCHEMA habit_tracker");
            stmt.execute("CREATE TABLE habit_tracker.users (id SERIAL PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255), role VARCHAR(255), is_blocked BOOLEAN)");
        }
    }

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @AfterAll
    static void tearDownDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP SCHEMA habit_tracker CASCADE");
        }
    }

    @Test
    @DisplayName("Register user functionality")
    void givenNewUser_whenAddUser_thenUserIsAdded() {
        // Given
        User user = UserUtils.getFirstUser();

        // When
        boolean isAdded = userRepository.addUser(user);

        // Then
        assertTrue(isAdded);
        assertNotNull(user.getId());
    }

    @Test
    @DisplayName("Check if user exists functionality")
    void givenExistingUser_whenIsExist_thenUserExists() {
        // Given
        User user = UserUtils.getFirstUser();
        userRepository.addUser(user);

        // When
        boolean exists = userRepository.isExist(user.getEmail());

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Check if non-existent user does not exist functionality")
    void givenNonExistentUser_whenIsExist_thenUserDoesNotExist() {
        // Given
        String email = "nonexistent@mail.com";

        // When
        boolean exists = userRepository.isExist(email);

        // Then
        assertFalse(exists);
    }

    @Test
    @DisplayName("Find user by email functionality")
    void givenExistingUser_whenFindByEmail_thenUserIsFound() {
        // Given
        User user = UserUtils.getFirstUser();
        userRepository.addUser(user);

        // When
        User foundUser = userRepository.findByEmail(user.getEmail());

        // Then
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    @DisplayName("Find non-existent user by email functionality")
    void givenNonExistentUser_whenFindByEmail_thenUserNotFoundExceptionIsThrown() {
        // Given
        String email = "nonexistent@mail.com";

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userRepository.findByEmail(email));
    }

    @Test
    @DisplayName("Delete user functionality")
    void givenExistingUser_whenDeleteUser_thenUserIsDeleted() {
        // Given
        User user = UserUtils.getFirstUser();
        userRepository.addUser(user);

        // When
        boolean isDeleted = userRepository.deleteUser(user.getEmail());

        // Then
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Delete non-existent user functionality")
    void givenNonExistentUser_whenDeleteUser_thenUserIsNotDeleted() {
        // Given
        String email = "nonexistent@mail.com";

        // When
        boolean isDeleted = userRepository.deleteUser(email);

        // Then
        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("Find all users functionality")
    void givenMultipleUsers_whenFindAll_thenAllUsersAreRetrieved() {
        // Given
        User user1 = UserUtils.getFirstUser();
        User user2 = UserUtils.getSecondUser();
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertEquals(3, users.size());
    }
}