package homework1.service.impl;

import homework1.dto.*;
import homework1.entity.Habit;
import homework1.entity.User;
import homework1.exception.HabitNotFoundException;
import homework1.service.HabitService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link HabitService} для управления привычками.
 */
public class HabitServiceImpl implements HabitService {
    @Override
    public boolean createHabit(CreateHabitDto createHabitDto) {
        createHabitDto.getUser()
                .getHabits()
                .add(createHabitDto.getHabit());
        return true;
    }

    @Override
    public boolean updateHabit(UpdateHabitDto updateHabitDto) {
        Habit habit = getHabitByName(updateHabitDto.getUser(), updateHabitDto.getOldHabitName());

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

        return true;
    }

    @Override
    public boolean deleteHabit(DeleteHabitDto deleteHabitDto) {
        return deleteHabitDto.getUser()
                .getHabits()
                .removeIf(habit -> habit.getName()
                        .equals(deleteHabitDto.getName()));
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        return Collections.unmodifiableSet(user.getHabits());
    }

    @Override
    public List<Habit> getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        return getHabitsByCreationDateDto.getUser()
                .getHabits()
                .stream()
                .filter(habit -> habit.getCreationDate().equals(getHabitsByCreationDateDto.getCreationDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Habit> getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        return getHabitsByFrequencyDateDto.getUser()
                .getHabits()
                .stream()
                .filter(habit -> habit.getFrequency().equals(getHabitsByFrequencyDateDto.getFrequency()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean markHabitAsCompleted(CompletionHabitDto completionHabitDto) {
        Habit habit = getHabitByName(completionHabitDto.getUser(), completionHabitDto.getHabitName());
        habit.getCompletions().add(completionHabitDto.getDate());
        habit.setCompleted(true);
        //в логике scheduler в зависимости от frequency, будет ставится completed -> false с частотой.
        //также использоваться при отправке уведомления
        return true;
    }

    @Override
    public long countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        Habit habit = getHabitByName(countHabitCompletionsForPeriodDto.getUser(), countHabitCompletionsForPeriodDto.getHabitName());
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (countHabitCompletionsForPeriodDto.getPeriod()) {
            case DAY -> startDate = now.minusDays(1);

            case WEEK -> startDate = now.minusWeeks(1);

            case MONTH -> startDate = now.minusMonths(1);

            default ->
                    throw new IllegalArgumentException("Unsupported period: " + countHabitCompletionsForPeriodDto.getPeriod());
        }

        return habit.getCompletions().stream()
                .filter(cd -> cd.isAfter(startDate) || cd.isEqual(startDate))
                .filter(cd -> cd.isBefore(now) || cd.isEqual(now))
                .count();
    }

    @Override
    public int getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        Habit habit = getHabitByName(getCurrentStreakDto.getUser(), getCurrentStreakDto.getName());

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
        Habit habit = getHabitByName(getCompletionPercentageDto.getUser(), getCompletionPercentageDto.getName());
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
                total = getCompletionPercentageDto.getStartDate()
                        .datesUntil(getCompletionPercentageDto.getEndDate().plusDays(1)).count();
                completed = habitCompletions.stream()
                        .filter(date -> !date.isBefore(getCompletionPercentageDto.getStartDate()) &&
                                !date.isAfter(getCompletionPercentageDto.getEndDate())).count();
            }
            case WEEKLY -> {
                total = startDate.datesUntil(endDate.plusDays(1))
                        .filter(date -> date.getDayOfWeek() == startDate.getDayOfWeek())
                        .count();
                completed = habitCompletions.stream()
                        .filter(date -> !date.isBefore(getCompletionPercentageDto.getStartDate()) && !date.isAfter(getCompletionPercentageDto.getEndDate())
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
        Set<Habit> habits = generateUserProgressReportDto.getUser().getHabits();
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
    /**
     * Вспомогательный метод для поиска привычки пользователя по имени.
     *
     * @param user Пользователь, чья привычка ищется.
     * @param name Имя привычки, которую нужно найти.
     * @return Привычка с указанным именем.
     * @throws HabitNotFoundException если привычка с указанным именем не найдена.
     */
    private Habit getHabitByName(User user, String name) {
        return user.getHabits().stream()
                .filter(h -> h.getName().equals(name))
                .findFirst()
                .orElseThrow(HabitNotFoundException::new);
    }
}
