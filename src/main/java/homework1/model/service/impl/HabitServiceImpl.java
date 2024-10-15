package homework1.model.service.impl;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.service.HabitService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link HabitService} для управления привычками.
 */
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public boolean createHabit(CreateHabitDto createHabitDto) {
        return habitRepository.createHabit(createHabitDto.getUser(), createHabitDto.getHabit());
    }

    @Override
    public boolean updateHabit(UpdateHabitDto updateHabitDto) {
        Habit habit = habitRepository.getHabitByName(updateHabitDto.getUser(), updateHabitDto.getOldHabitName());

        String newHabitName = updateHabitDto.getNewHabitName();
        if (Objects.nonNull(newHabitName)) {
            habit.setName(newHabitName);
        }

        String newHabitDescription = updateHabitDto.getNewHabitDescription();
        if (Objects.nonNull(newHabitDescription)) {
            habit.setDescription(newHabitDescription);
        }

        Habit.Frequency newFrequency = updateHabitDto.getNewFrequency();
        if (Objects.nonNull(newFrequency)) {
            habit.setFrequency(updateHabitDto.getNewFrequency());
        }

        return habitRepository.updateHabit(updateHabitDto.getUser(), habit);
    }

    @Override
    public boolean deleteHabit(DeleteHabitDto deleteHabitDto) {
        return habitRepository.deleteHabit(deleteHabitDto.getUser(), deleteHabitDto.getName());
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        return Collections.unmodifiableSet(habitRepository.getAllHabits(user));
    }

    @Override
    public Set<Habit> getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        return habitRepository.getAllHabits(getHabitsByCreationDateDto.getUser())
                .stream()
                .filter(habit -> habit.getCreationDate().equals(getHabitsByCreationDateDto.getCreationDate()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Habit> getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        return habitRepository.getAllHabits(getHabitsByFrequencyDateDto.getUser())
                .stream()
                .filter(habit -> habit.getFrequency().equals(getHabitsByFrequencyDateDto.getFrequency()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean markHabitAsCompleted(CompletionHabitDto completionHabitDto) {
        Habit habit = habitRepository.getHabitByName(completionHabitDto.getUser(), completionHabitDto.getHabitName());
        habit.getCompletions().add(completionHabitDto.getDate());
        habit.setCompleted(true);
        return true;
    }

    @Override
    public long countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        Habit habit = habitRepository.getHabitByName(countHabitCompletionsForPeriodDto.getUser(), countHabitCompletionsForPeriodDto.getHabitName());
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (countHabitCompletionsForPeriodDto.getPeriod()) {
            case DAY -> startDate = now.minusDays(1);
            case WEEK -> startDate = now.minusWeeks(1);
            case MONTH -> startDate = now.minusMonths(1);
            default -> throw new IllegalArgumentException("Unsupported period: " + countHabitCompletionsForPeriodDto.getPeriod());
        }

        return habit.getCompletions().stream()
                .filter(cd -> cd.isAfter(startDate) || cd.isEqual(startDate))
                .filter(cd -> cd.isBefore(now) || cd.isEqual(now))
                .count();
    }

    @Override
    public int getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        Habit habit = habitRepository.getHabitByName(getCurrentStreakDto.getUser(), getCurrentStreakDto.getName());

        if (Objects.isNull(habit)) {
            throw new HabitNotFoundException();
        }

        List<LocalDate> completions = habit.getCompletions();

        if (Objects.isNull(completions) || completions.isEmpty()) {
            return 0;
        }

        completions.sort(LocalDate::compareTo);

        int currentStreak = 1;
        LocalDate previousDate = completions.getLast();

        for (int i = completions.size() - 2; i >= 0; i--) {
            LocalDate currentDate = completions.get(i);

            switch (habit.getFrequency()) {
                case DAILY:
                    if (currentDate.isEqual(previousDate.minusDays(1))) {
                        currentStreak++;
                    } else {
                        return currentStreak;
                    }
                    break;
                case WEEKLY:
                    if (currentDate.isEqual(previousDate.minusWeeks(1))) {
                        currentStreak++;
                    } else {
                        return currentStreak;
                    }
                    break;
            }
            previousDate = currentDate;
        }

        return currentStreak;
    }

    @Override
    public double getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto) {
        Habit habit = habitRepository.getHabitByName(getCompletionPercentageDto.getUser(), getCompletionPercentageDto.getName());
        if (Objects.isNull(habit)) {
            throw new HabitNotFoundException();
        }

        long total = 0;
        long completed = 0;
        LocalDate startDate = getCompletionPercentageDto.getStartDate();
        LocalDate endDate = getCompletionPercentageDto.getEndDate();
        List<LocalDate> habitCompletions = habit.getCompletions();
        switch (habit.getFrequency()) {
            case DAILY -> {
                total = startDate.datesUntil(endDate.plusDays(1)).count();
                completed = habitCompletions.stream()
                        .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate)).count();
            }
            case WEEKLY -> {
                total = startDate.datesUntil(endDate.plusDays(1))
                        .filter(date -> date.getDayOfWeek() == startDate.getDayOfWeek()).count();
                completed = habitCompletions.stream()
                        .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate)
                                && date.getDayOfWeek() == startDate.getDayOfWeek()).count();
            }
        }

        if (total == 0) {
            throw new IllegalArgumentException("Wrong date in getCompletionPercentageDto");
        }

        return (double) completed / total * 100;
    }

    @Override
    public UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto) {
        Set<Habit> habits = habitRepository.getAllHabits(generateUserProgressReportDto.getUser());
        List<UserProgressReportDto.HabitProgress> habitProgresses = habits.stream()
                .map(habit -> {
                    int streak = getCurrentStreak(new GetCurrentStreakDto(generateUserProgressReportDto.getUser(), habit.getName()));
                    double completionPercentage = getCompletionPercentage(new GetCompletionPercentageDto(
                            generateUserProgressReportDto.getUser(),
                            habit.getName(),
                            generateUserProgressReportDto.getStartDate(),
                            generateUserProgressReportDto.getEndDate()
                    ));
                    return UserProgressReportDto.HabitProgress.builder()
                            .streak(streak)
                            .completionPercentage(completionPercentage)
                            .habit(habit)
                            .build();
                }).toList();

        return UserProgressReportDto.builder()
                .user(generateUserProgressReportDto.getUser())
                .habitProgresses(habitProgresses)
                .build();
    }
}