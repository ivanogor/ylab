package repository;

import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.repository.UserRepository;
import homework1.model.repository.impl.HabitRepositoryImpl;
import homework1.model.repository.impl.UserRepositoryImpl;
import homework1.model.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.HabitUtils;
import utils.UserUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class HabitRepositoryImplTests {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("ylab")
            .withUsername("username")
            .withPassword("password");

    private HabitRepository habitRepository;
    private User testUser;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        DatabaseConnection.setTestConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE SCHEMA habit_tracker");
            stmt.execute("CREATE TABLE habit_tracker.habits (id SERIAL PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), frequency VARCHAR(255), creation_date DATE, completions VARCHAR(255), is_completed BOOLEAN)");
            stmt.execute("CREATE TABLE habit_tracker.user_habits (user_id BIGINT, habit_id BIGINT)");
            stmt.execute("CREATE TABLE habit_tracker.users (id SERIAL PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255), role VARCHAR(255), is_blocked BOOLEAN)");
        }
    }

    @BeforeEach
    void setUp() {
        habitRepository = new HabitRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        testUser = UserUtils.getFirstUser();
        userRepository.addUser(testUser);
    }

    @AfterAll
    static void tearDownDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP SCHEMA habit_tracker CASCADE");
        }
    }

    @Test
    @DisplayName("Create habit functionality")
    void givenNewHabit_whenCreateHabit_thenHabitIsCreated() {
        // Given
        Habit habit = HabitUtils.getFirstHabit();

        // When
        boolean isCreated = Objects.nonNull(habitRepository.createHabit(testUser, habit));

        // Then
        assertTrue(isCreated);
    }

    @Test
    @DisplayName("Update habit functionality")
    void givenExistingHabit_whenUpdateHabit_thenHabitIsUpdated() {
        // Given
        Habit habit = HabitUtils.getFirstHabit();
        habitRepository.createHabit(testUser, habit);

        // When
        habit.setDescription("Updated Description");
        boolean isUpdated = habitRepository.updateHabit(testUser, habit);

        // Then
        assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Delete habit functionality")
    void givenExistingHabit_whenDeleteHabit_thenHabitIsDeleted() {
        // Given
        Habit habit = HabitUtils.getFirstHabit();
        habitRepository.createHabit(testUser, habit);

        // When
        boolean isDeleted = habitRepository.deleteHabit(testUser, "habitOne");

        // Then
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Get all habits functionality")
    void givenMultipleHabits_whenGetAllHabits_thenAllHabitsAreRetrieved() {
        // Given
        Habit habit1 = HabitUtils.getFirstHabit();
        Habit habit2 = HabitUtils.getSecondHabit();
        habitRepository.createHabit(testUser, habit1);
        habitRepository.createHabit(testUser, habit2);

        // When
        Set<Habit> habits = habitRepository.getAllHabits(testUser);

        // Then
        assertEquals(2, habits.size());
    }

    @Test
    @DisplayName("Get habit by name functionality")
    void givenExistingHabit_whenGetHabitByName_thenHabitIsRetrieved() {
        // Given
        Habit habit = HabitUtils.getFirstHabit();
        habitRepository.createHabit(testUser, habit);

        // When
        Habit foundHabit = habitRepository.getHabitByName(testUser, "habitOne");

        // Then
        assertNotNull(foundHabit);
        assertEquals("habitOne", foundHabit.getName());
    }

    @Test
    @DisplayName("Get non-existent habit by name functionality")
    void givenNonExistentHabit_whenGetHabitByName_thenHabitNotFoundExceptionIsThrown() {
        // Given
        // No habits created

        // When & Then
        assertThrows(HabitNotFoundException.class, () -> {
            habitRepository.getHabitByName(testUser, "NonExistentHabit");
        });
    }
}