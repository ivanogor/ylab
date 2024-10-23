package service;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.service.impl.HabitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.HabitUtils;
import utils.UserUtils;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceImplTests {

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitServiceImpl habitService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = UserUtils.getFirstUser();
    }

    @Test
    @DisplayName("Create habit functionality")
    void givenNewHabit_whenCreateHabit_thenHabitIsCreated() {
        // Given
        CreateHabitDto createHabitDto = HabitUtils.getCreateHabitDto();
        when(habitRepository.createHabit(any(User.class), any(Habit.class))).thenReturn(createHabitDto.getHabit());

        // When
        boolean isCreated = habitService.createHabit(createHabitDto);

        // Then
        assertTrue(isCreated);
        verify(habitRepository, times(1)).createHabit(any(User.class), any(Habit.class));
    }

    @Test
    @DisplayName("Update habit functionality")
    void givenExistingHabit_whenUpdateHabit_thenHabitIsUpdated() {
        // Given
        UpdateHabitDto updateHabitDto = HabitUtils.getUpdateHabitDto();
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(HabitUtils.getFirstHabit());
        when(habitRepository.updateHabit(any(User.class), any(Habit.class))).thenReturn(true);

        // When
        boolean isUpdated = habitService.updateHabit(updateHabitDto);

        // Then
        assertTrue(isUpdated);
        verify(habitRepository, times(1)).updateHabit(any(User.class), any(Habit.class));
    }

    @Test
    @DisplayName("Delete habit functionality")
    void givenExistingHabit_whenDeleteHabit_thenHabitIsDeleted() {
        // Given
        DeleteHabitDto deleteHabitDto = HabitUtils.getDeleteHabitDto();
        when(habitRepository.deleteHabit(any(User.class), anyString())).thenReturn(true);

        // When
        boolean isDeleted = habitService.deleteHabit(deleteHabitDto);

        // Then
        assertTrue(isDeleted);
        verify(habitRepository, times(1)).deleteHabit(any(User.class), anyString());
    }

    @Test
    @DisplayName("Get all habits functionality")
    void givenMultipleHabits_whenGetAllHabits_thenAllHabitsAreRetrieved() {
        // Given
        when(habitRepository.getAllHabits(any(User.class))).thenReturn(HabitUtils.getAllHabits());

        // When
        Set<Habit> habits = habitService.getAllHabits(testUser);

        // Then
        assertEquals(2, habits.size());
        verify(habitRepository, times(1)).getAllHabits(any(User.class));
    }

    @Test
    @DisplayName("Get habits by creation date functionality")
    void givenHabitsWithSameCreationDate_whenGetHabitsByCreationDate_thenHabitsAreRetrieved() {
        // Given
        GetHabitsByCreationDateDto getHabitsByCreationDateDto = HabitUtils.getGetHabitsByCreationDateDto();
        when(habitRepository.getAllHabits(any(User.class))).thenReturn(HabitUtils.getAllHabits());

        // When
        Set<Habit> habits = habitService.getHabitsByCreationDate(getHabitsByCreationDateDto);

        // Then
        assertEquals(1, habits.size());
        verify(habitRepository, times(1)).getAllHabits(any(User.class));
    }

    @Test
    @DisplayName("Get habits by frequency functionality")
    void givenHabitsWithSameFrequency_whenGetHabitsByFrequency_thenHabitsAreRetrieved() {
        // Given
        GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto = HabitUtils.getHabitsByFrequencyDateDto();
        when(habitRepository.getAllHabits(any(User.class))).thenReturn(HabitUtils.getAllHabits());

        // When
        Set<Habit> habits = habitService.getHabitsByFrequency(getHabitsByFrequencyDateDto);

        // Then
        assertEquals(1, habits.size());
        verify(habitRepository, times(1)).getAllHabits(any(User.class));
    }

    @Test
    @DisplayName("Mark habit as completed functionality")
    void givenExistingHabit_whenMarkHabitAsCompleted_thenHabitIsMarkedAsCompleted() {
        // Given
        CompletionHabitDto completionHabitDto = HabitUtils.getCompletionHabitDto();
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(HabitUtils.getFirstHabit());
        when(habitRepository.updateHabit(any(User.class), any(Habit.class))).thenReturn(true);

        // When
        boolean isCompleted = habitService.markHabitAsCompleted(completionHabitDto);

        // Then
        assertTrue(isCompleted);
        verify(habitRepository, times(1)).updateHabit(any(User.class), any(Habit.class));
    }

    @Test
    @DisplayName("Count habit completions for period functionality")
    void givenExistingHabit_whenCountHabitCompletionsForPeriod_thenCompletionsAreCounted() {
        // Given
        CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto = HabitUtils.getCountHabitCompletionsForPeriodDto();
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(HabitUtils.getSecondHabit());

        // When
        long completionsCount = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);

        // Then
        assertEquals(2, completionsCount);
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }

    @Test
    @DisplayName("Get current streak functionality")
    void givenExistingHabit_whenGetCurrentStreak_thenCurrentStreakIsRetrieved() {
        // Given
        GetCurrentStreakDto getCurrentStreakDto = HabitUtils.getCurrentStreakDto();
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(HabitUtils.getSecondHabit());

        // When
        int currentStreak = habitService.getCurrentStreak(getCurrentStreakDto);

        // Then
        assertEquals(3, currentStreak);
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }

    @Test
    @DisplayName("Get completion percentage functionality")
    void givenExistingHabit_whenGetCompletionPercentage_thenCompletionPercentageIsRetrieved() {
        // Given
        GetCompletionPercentageDto getCompletionPercentageDto = HabitUtils.getCompletionPercentageDto();
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(HabitUtils.getFirstHabit());

        // When
        double completionPercentage = habitService.getCompletionPercentage(getCompletionPercentageDto);

        // Then
        assertEquals(75.0, completionPercentage);
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }

    @Test
    @DisplayName("Generate user progress report functionality")
    void givenExistingUser_whenGenerateUserProgressReport_thenUserProgressReportIsGenerated() {
        // Given
        GenerateUserProgressReportDto generateUserProgressReportDto = HabitUtils.getUserProgressReportDto();
        when(habitRepository.getAllHabits(any(User.class))).thenReturn(HabitUtils.getAllHabits());
        when(habitRepository.getHabitByName(any(User.class), eq("habitOne"))).thenReturn(HabitUtils.getFirstHabit());
        when(habitRepository.getHabitByName(any(User.class), eq("habitTwo"))).thenReturn(HabitUtils.getSecondHabit());

        // When
        UserProgressReportDto userProgressReportDto = habitService.generateUserProgressReport(generateUserProgressReportDto);

        // Then
        assertNotNull(userProgressReportDto);
        assertEquals(2, userProgressReportDto.getHabitProgresses().size());
        verify(habitRepository, times(1)).getAllHabits(any(User.class));
    }

    @Test
    @DisplayName("Get current streak functionality with no completions")
    void givenExistingHabitWithNoCompletions_whenGetCurrentStreak_thenCurrentStreakIsZero() {
        // Given
        GetCurrentStreakDto getCurrentStreakDto = HabitUtils.getCurrentStreakDtoWithNoCompletions();
        Habit habit = HabitUtils.getFirstHabit();
        habit.setCompletions(Collections.emptyList());
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(habit);

        // When
        int currentStreak = habitService.getCurrentStreak(getCurrentStreakDto);

        // Then
        assertEquals(0, currentStreak);
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }

    @Test
    @DisplayName("Get completion percentage functionality with no completions")
    void givenExistingHabitWithNoCompletions_whenGetCompletionPercentage_thenCompletionPercentageIsZero() {
        // Given
        GetCompletionPercentageDto getCompletionPercentageDto = HabitUtils.getCompletionPercentageDtoWithNoCompletions();
        Habit habit = HabitUtils.getFirstHabit();
        habit.setCompletions(Collections.emptyList());
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenReturn(habit);

        // When
        double completionPercentage = habitService.getCompletionPercentage(getCompletionPercentageDto);

        // Then
        assertEquals(0.0, completionPercentage);
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }

    @Test
    @DisplayName("Generate user progress report functionality with no habits")
    void givenExistingUserWithNoHabits_whenGenerateUserProgressReport_thenUserProgressReportIsEmpty() {
        // Given
        GenerateUserProgressReportDto generateUserProgressReportDto = HabitUtils.getUserProgressReportDtoWithNoHabits();
        when(habitRepository.getAllHabits(any(User.class))).thenReturn(Collections.emptySet());

        // When
        UserProgressReportDto userProgressReportDto = habitService.generateUserProgressReport(generateUserProgressReportDto);

        // Then
        assertNotNull(userProgressReportDto);
        assertEquals(0, userProgressReportDto.getHabitProgresses().size());
        verify(habitRepository, times(1)).getAllHabits(any(User.class));
    }

    @Test
    @DisplayName("Get habit by name functionality with non-existent habit")
    void givenNonExistentHabit_whenGetHabitByName_thenHabitNotFoundExceptionIsThrown() {
        // Given
        when(habitRepository.getHabitByName(any(User.class), anyString())).thenThrow(new HabitNotFoundException());

        // When & Then
        assertThrows(HabitNotFoundException.class, () -> habitService.getHabitByName(testUser, "NonExistentHabit"));
        verify(habitRepository, times(1)).getHabitByName(any(User.class), anyString());
    }
}