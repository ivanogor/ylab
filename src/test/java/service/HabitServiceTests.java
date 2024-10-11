package service;

import homework1.dto.*;
import homework1.entity.Habit;
import homework1.entity.User;
import homework1.service.HabitService;
import homework1.service.impl.HabitServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import utils.HabitUtils;
import utils.UserUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabitServiceTests {
    private final HabitService habitService = new HabitServiceImpl();

    @Test
    @DisplayName("Create habit functionality")
    public void givenCreateHabitDto_whenCreateHabit_thenHabitIsCreated() {
        //given
        CreateHabitDto createHabitDto = HabitUtils.getCreateHabitDto();
        User actualUser = createHabitDto.getUser();
        Habit habit = createHabitDto.getHabit();
        assertThat(actualUser.getHabits().size()).isEqualTo(2);
        //when
        boolean result = habitService.createHabit(createHabitDto);
        //then
        assertTrue(result);
        assertThat(actualUser.getHabits().size()).isEqualTo(3);
        assertThat(actualUser.getHabits()).contains(habit);
    }

    @Test
    @DisplayName("Update habit functionality")
    public void givenUpdateHabitDto_whenUpdateHabit_thenHabitIsUpdated() {
        //given
        UpdateHabitDto updateHabitDto = HabitUtils.getUpdateHabitDto();
        Habit actualHabit = updateHabitDto.getUser().getHabits().stream().filter(habit -> habit.getName().equals("habitOne")).findFirst().orElseThrow();
        Habit expectedHabit = HabitUtils.getFirstHabit();
        expectedHabit.setName(updateHabitDto.getNewHabitName());
        expectedHabit.setDescription(updateHabitDto.getNewHabitDescription());
        expectedHabit.setFrequency(updateHabitDto.getNewFrequency());
        //when
        boolean result = habitService.updateHabit(updateHabitDto);
        //then
        assertTrue(result);
        assertThat(actualHabit.getDescription()).isEqualTo(updateHabitDto.getNewHabitDescription());
    }

    @Test
    @DisplayName("Delete habit functionality")
    public void givenDeleteHabitDto_whenDeleteHabit_thenHabitIsDeleted() {
        //given
        DeleteHabitDto deleteHabitDto = HabitUtils.getDeleteHabitDto();
        User user = deleteHabitDto.getUser();
        assertThat(user.getHabits().size()).isEqualTo(2);
        //when
        boolean result = habitService.deleteHabit(deleteHabitDto);
        //then
        assertTrue(result);
        assertThat(user.getHabits().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Get all habit functionality")
    public void givenUser_whenGetAllHabits_thenHabitsAreReturned() {
        //given
        User user = UserUtils.getFirstUser();
        Set<Habit> expected = user.getHabits();
        //when
        Set<Habit> actual = habitService.getAllHabits(user);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Get habits by creation functionality")
    public void givenGetHabitsByCreationDateDto_whenGetHabitsByCreationDate_thenHabitsAreReturned() {
        //given
        GetHabitsByCreationDateDto getHabitsByCreationDateDto = HabitUtils.getGetHabitsByCreationDateDto();
        User user = getHabitsByCreationDateDto.getUser();
        List<Habit> expected = List.of(HabitUtils.getFirstHabit());
        assertThat(user.getHabits().size()).isEqualTo(2);
        //when
        List<Habit> actual = habitService.getHabitsByCreationDate(getHabitsByCreationDateDto);
        //then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Get habits by frequency functionality")
    public void givenGetHabitsByFrequencyDateDto_whenGetHabitsByFrequency_thenHabitsAreReturned() {
        //given
        GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto = HabitUtils.getHabitsByFrequencyDateDto();
        User user = getHabitsByFrequencyDateDto.getUser();
        List<Habit> expected = List.of(HabitUtils.getSecondHabit());
        assertThat(user.getHabits().size()).isEqualTo(2);
        //when
        List<Habit> actual = habitService.getHabitsByFrequency(getHabitsByFrequencyDateDto);
        //then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Mark habit as complete and completion adds for habit functionality")
    public void givenCompletionHabitDto_whenMarkHabitAsCompleted_thenHabitIsMarkedAndCompletionAdded() {
        //given
        CompletionHabitDto completionHabitDto = HabitUtils.getCompletionHabitDto();
        Habit habit =  completionHabitDto.getUser().getHabits().stream().filter(h -> h.getName().equals("habitOne")).findFirst().orElseThrow();;
        List<LocalDate> completions = habit.getCompletions();
        assertThat(completions).hasSize(3);
        assertFalse(habit.isCompleted());
        //when
        boolean actual = habitService.markHabitAsCompleted(completionHabitDto);
        //then
        assertTrue(actual);
        assertTrue(habit.isCompleted());
        assertThat(completions).hasSize(4);
    }

    @Test
    @DisplayName("Get completion count functionality")
    public void givenCountHabitCompletionsForPeriodDto_whenCountHabitCompletionsForPeriod_thenCompletionCountForPeriodIsReturned() {
        //PERIOD - WEEK
        //given
        CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto = HabitUtils.getCountHabitCompletionsForPeriodDto();
        //when
        long actual = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);
        //then
        assertThat(actual).isEqualTo(2);


        //PERIOD - MONTH
        //given
        countHabitCompletionsForPeriodDto.setPeriod(CountHabitCompletionsForPeriodDto.Period.MONTH);
        //when
        actual = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);
        //then
        assertThat(actual).isEqualTo(3);
    }

    @Test
    @DisplayName("Get current streak functionality")
    public void givenGetCurrentStreakDto_whenGetCurrentStreak_thenStreakIsReturned() {
        //SHOULD RETURN 3
        // Completion -> LocalDate.now(), LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2)
        //given
        GetCurrentStreakDto getCurrentStreakDto = HabitUtils.getCurrentStreakDto();
        //when
        int actual = habitService.getCurrentStreak(getCurrentStreakDto);
        //then
        assertThat(actual).isEqualTo(3);

        //SHOULD RETURN 2
        // Completion -> LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now().minusDays(3)
        //given
        getCurrentStreakDto.setName("habitOne");
        //when
        actual = habitService.getCurrentStreak(getCurrentStreakDto);
        //then
        assertThat(actual).isEqualTo(2);
    }

    @Test
    @DisplayName("Get current streak functionality")
    public void givenGetCompletionPercentageDto_whenGetCompletionPercentage_thenPercentageIsReturned() {
        // SHOULD RETURN 75
        //given
        GetCompletionPercentageDto getCompletionPercentageDto = HabitUtils.getCompletionPercentageDto();
        //when
        double actual = habitService.getCompletionPercentage(getCompletionPercentageDto);
        //then
        assertThat(actual).isEqualTo(75);

        // SHOULD RETURN 100
        //given
        getCompletionPercentageDto.setName("habitTwo");
        getCompletionPercentageDto.setStartDate(LocalDate.now().minusWeeks(2));
        getCompletionPercentageDto.setEndDate(LocalDate.now());
        //when
        actual = habitService.getCompletionPercentage(getCompletionPercentageDto);
        //then
        assertThat(actual).isEqualTo(100);
    }

    @Test
    @DisplayName("Get current streak functionality")
    public void givenGenerateUserProgressReportDto_whenGenerateUserProgressReport_thenUserProgressReportDtoIsReturned() {
        //given
        GenerateUserProgressReportDto generateUserProgressReportDto = HabitUtils.getUserProgressReportDto();
        //when
        UserProgressReportDto actual = habitService.generateUserProgressReport(generateUserProgressReportDto);
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getHabitProgresses().size()).isEqualTo(2);
        assertThat(actual.getHabitProgresses().getFirst().getStreak()).isEqualTo(2);
        assertThat(actual.getHabitProgresses().getFirst().getCompletionPercentage()).isEqualTo(75);
        assertThat(actual.getHabitProgresses().get(1).getStreak()).isEqualTo(3);
        assertThat(actual.getHabitProgresses().get(1).getCompletionPercentage()).isEqualTo(0);
    }
}
