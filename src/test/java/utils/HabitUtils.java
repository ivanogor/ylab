package utils;

import homework1.dto.*;
import homework1.entity.Habit;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class HabitUtils {

    public static Habit getFirstHabit() {
        return Habit.builder()
                .name("habitOne")
                .description("descriptionOne")
                .isCompleted(false)
                .frequency(Habit.Frequency.DAILY)
                .creationDate(LocalDate.now().minusDays(3))
                .completions(new ArrayList<>(List.of(LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now().minusDays(3))))
                .build();
    }

    public static Habit getSecondHabit() {
        return Habit.builder()
                .name("habitTwo")
                .description("descriptionTwo")
                .isCompleted(true)
                .frequency(Habit.Frequency.WEEKLY)
                .creationDate(LocalDate.now().minusWeeks(2))
                .completions(new ArrayList<>(List.of(LocalDate.now(), LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2))))
                .build();
    }

    public static Habit getThirdHabit() {
        return Habit.builder()
                .name("habitThree")
                .description("descriptionThree")
                .isCompleted(true)
                .frequency(Habit.Frequency.WEEKLY)
                .creationDate(LocalDate.now().minusWeeks(2))
                .completions(new ArrayList<>(List.of(LocalDate.now(), LocalDate.now().minusWeeks(1), LocalDate.now().minusDays(5))))
                .build();
    }


    public static CreateHabitDto getCreateHabitDto() {
        return CreateHabitDto.builder()
                .habit(getThirdHabit())
                .user(UserUtils.getFirstUser())
                .build();
    }

    public static Set<Habit> getAllHabits() {
        Set<Habit> habits = new HashSet<>();
        habits.add(getFirstHabit());
        habits.add(getSecondHabit());
        return habits;
    }

    public static UpdateHabitDto getUpdateHabitDto() {
        return UpdateHabitDto.builder()
                .user(UserUtils.getFirstUser())
                .oldHabitName("habitOne")
                .newHabitName("newHabitOne")
                .newHabitDescription("newHabitDescriptionOne")
                .newFrequency(Habit.Frequency.WEEKLY)
                .build();
    }

    public static DeleteHabitDto getDeleteHabitDto() {
        return DeleteHabitDto.builder()
                .user(UserUtils.getFirstUser())
                .name("habitOne")
                .build();
    }

    public static GetHabitsByCreationDateDto getGetHabitsByCreationDateDto() {
        return GetHabitsByCreationDateDto.builder()
                .user(UserUtils.getFirstUser())
                .creationDate(LocalDate.now().minusDays(3))
                .build();
    }

    public static GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto() {
        return GetHabitsByFrequencyDateDto.builder()
                .user(UserUtils.getFirstUser())
                .frequency(Habit.Frequency.WEEKLY)
                .build();
    }

    public static CompletionHabitDto getCompletionHabitDto() {
        return CompletionHabitDto.builder()
                .user(UserUtils.getFirstUser())
                .date(LocalDate.now())
                .habitName("habitOne")
                .build();
    }

    public static CountHabitCompletionsForPeriodDto getCountHabitCompletionsForPeriodDto() {
        return CountHabitCompletionsForPeriodDto.builder()
                .user(UserUtils.getFirstUser())
                .habitName("habitTwo")
                .period(CountHabitCompletionsForPeriodDto.Period.WEEK)
                .build();
    }

    public static GetCurrentStreakDto getCurrentStreakDto() {
        return GetCurrentStreakDto.builder()
                .user(UserUtils.getFirstUser())
                .name("habitTwo")
                .build();
    }

    public static GetCompletionPercentageDto getCompletionPercentageDto() {
        return GetCompletionPercentageDto.builder()
                .user(UserUtils.getFirstUser())
                .name("habitOne")
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now())
                .build();
    }

    public static GenerateUserProgressReportDto getUserProgressReportDto() {
        return GenerateUserProgressReportDto.builder()
                .user(UserUtils.getFirstUser())
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now())
                .build();
    }
}
