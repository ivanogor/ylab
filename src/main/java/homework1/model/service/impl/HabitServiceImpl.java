package homework1.model.service.impl;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link HabitService} для управления привычками.
 */
@Slf4j
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public boolean createHabit(CreateHabitDto createHabitDto) {
        if (createHabitDto == null || createHabitDto.getUser() == null || createHabitDto.getHabit() == null) {
            log.error("Invalid input data for creating habit: {}", createHabitDto);
            return false;
        }
        Habit result = habitRepository.createHabit(createHabitDto.getUser(), createHabitDto.getHabit());
        log.info("Habit creation result: {}", result);
        return Objects.nonNull(result);
    }

    @Override
    public boolean updateHabit(UpdateHabitDto updateHabitDto) {
        if (updateHabitDto == null || updateHabitDto.getUser() == null || updateHabitDto.getOldHabitName() == null) {
            log.error("Invalid input data for updating habit: {}", updateHabitDto);
            return false;
        }
        Habit habit = getHabitByName(updateHabitDto.getUser(), updateHabitDto.getOldHabitName());

        if (Objects.nonNull(updateHabitDto.getNewHabitName())) {
            habit.setName(updateHabitDto.getNewHabitName());
        }

        if (Objects.nonNull(updateHabitDto.getNewHabitDescription())) {
            habit.setDescription(updateHabitDto.getNewHabitDescription());
        }

        if (Objects.nonNull(updateHabitDto.getNewFrequency())) {
            habit.setFrequency(updateHabitDto.getNewFrequency());
        }

        boolean result = habitRepository.updateHabit(updateHabitDto.getUser(), habit);
        log.info("Habit update result: {}", result);
        return result;
    }

    @Override
    public boolean deleteHabit(DeleteHabitDto deleteHabitDto) {
        if (deleteHabitDto == null || deleteHabitDto.getUser() == null || deleteHabitDto.getName() == null) {
            log.error("Invalid input data for deleting habit: {}", deleteHabitDto);
            return false;
        }
        boolean result = habitRepository.deleteHabit(deleteHabitDto.getUser(), deleteHabitDto.getName());
        log.info("Habit deletion result: {}", result);
        return result;
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        if (user == null) {
            log.error("Invalid input data for getting all habits: user is null");
            return Collections.emptySet();
        }
        Set<Habit> habits = habitRepository.getAllHabits(user);
        log.info("All habits found for user: user_id={}, habits={}", user.getId(), habits);
        return habits;
    }

    @Override
    public Set<Habit> getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        if (getHabitsByCreationDateDto == null || getHabitsByCreationDateDto.getUser() == null || getHabitsByCreationDateDto.getCreationDate() == null) {
            log.error("Invalid input data for getting habits by creation date: {}", getHabitsByCreationDateDto);
            return Collections.emptySet();
        }
        Set<Habit> habits = habitRepository.getAllHabits(getHabitsByCreationDateDto.getUser())
                .stream()
                .filter(habit -> habit.getCreationDate().equals(getHabitsByCreationDateDto.getCreationDate()))
                .collect(Collectors.toUnmodifiableSet());
        log.info("Habits found by creation date: user_id={}, creation_date={}, habits={}", getHabitsByCreationDateDto.getUser().getId(), getHabitsByCreationDateDto.getCreationDate(), habits);
        return habits;
    }

    @Override
    public Set<Habit> getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        if (getHabitsByFrequencyDateDto == null || getHabitsByFrequencyDateDto.getUser() == null || getHabitsByFrequencyDateDto.getFrequency() == null) {
            log.error("Invalid input data for getting habits by frequency: {}", getHabitsByFrequencyDateDto);
            return Collections.emptySet();
        }
        Set<Habit> habits = habitRepository.getAllHabits(getHabitsByFrequencyDateDto.getUser())
                .stream()
                .filter(habit -> habit.getFrequency().equals(getHabitsByFrequencyDateDto.getFrequency()))
                .collect(Collectors.toUnmodifiableSet());
        log.info("Habits found by frequency: user_id={}, frequency={}, habits={}", getHabitsByFrequencyDateDto.getUser().getId(), getHabitsByFrequencyDateDto.getFrequency(), habits);
        return habits;
    }

    @Override
    public boolean markHabitAsCompleted(CompletionHabitDto completionHabitDto) {
        if (completionHabitDto == null || completionHabitDto.getUser() == null || completionHabitDto.getHabitName() == null || completionHabitDto.getDate() == null) {
            log.error("Invalid input data for marking habit as completed: {}", completionHabitDto);
            return false;
        }
        Habit habit = getHabitByName(completionHabitDto.getUser(), completionHabitDto.getHabitName());
        habit.getCompletions().add(completionHabitDto.getDate());
        habit.setCompleted(true);
        boolean result = habitRepository.updateHabit(completionHabitDto.getUser(), habit);
        log.info("Habit marked as completed: user_id={}, habit_name={}, result={}", completionHabitDto.getUser().getId(), completionHabitDto.getHabitName(), result);
        return result;
    }

    @Override
    public long countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        if (countHabitCompletionsForPeriodDto == null || countHabitCompletionsForPeriodDto.getUser() == null || countHabitCompletionsForPeriodDto.getHabitName() == null || countHabitCompletionsForPeriodDto.getPeriod() == null) {
            log.error("Invalid input data for counting habit completions for period: {}", countHabitCompletionsForPeriodDto);
            return 0;
        }
        Habit habit = getHabitByName(countHabitCompletionsForPeriodDto.getUser(), countHabitCompletionsForPeriodDto.getHabitName());
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (countHabitCompletionsForPeriodDto.getPeriod()) {
            case DAY -> startDate = now.minusDays(1);
            case WEEK -> startDate = now.minusWeeks(1);
            case MONTH -> startDate = now.minusMonths(1);
            default -> throw new IllegalArgumentException("Unsupported period: " + countHabitCompletionsForPeriodDto.getPeriod());
        }

        long count = habit.getCompletions().stream()
                .filter(cd -> cd.isAfter(startDate) || cd.isEqual(startDate))
                .filter(cd -> cd.isBefore(now) || cd.isEqual(now))
                .count();
        log.info("Habit completions count for period: user_id={}, habit_name={}, period={}, count={}", countHabitCompletionsForPeriodDto.getUser().getId(), countHabitCompletionsForPeriodDto.getHabitName(), countHabitCompletionsForPeriodDto.getPeriod(), count);
        return count;
    }

    @Override
    public int getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        if (getCurrentStreakDto == null || getCurrentStreakDto.getUser() == null || getCurrentStreakDto.getName() == null) {
            log.error("Invalid input data for getting current streak: {}", getCurrentStreakDto);
            return 0;
        }
        Habit habit = getHabitByName(getCurrentStreakDto.getUser(), getCurrentStreakDto.getName());

        if (Objects.isNull(habit)) {
            log.error("Habit not found: user_id={}, habit_name={}", getCurrentStreakDto.getUser().getId(), getCurrentStreakDto.getName());
            throw new HabitNotFoundException();
        }

        List<LocalDate> completions = habit.getCompletions();

        if (Objects.isNull(completions) || completions.isEmpty()) {
            log.info("No completions found for habit: user_id={}, habit_name={}", getCurrentStreakDto.getUser().getId(), getCurrentStreakDto.getName());
            return 0;
        }

        completions.sort(LocalDate::compareTo);

        int currentStreak = 1;
        LocalDate previousDate = completions.get(completions.size() - 1);

        for (int i = completions.size() - 2; i >= 0; i--) {
            LocalDate currentDate = completions.get(i);

            switch (habit.getFrequency()) {
                case DAILY:
                    if (currentDate.isEqual(previousDate.minusDays(1))) {
                        currentStreak++;
                    } else {
                        log.info("Current streak broken: user_id={}, habit_name={}, streak={}", getCurrentStreakDto.getUser().getId(), getCurrentStreakDto.getName(), currentStreak);
                        return currentStreak;
                    }
                    break;
                case WEEKLY:
                    if (currentDate.isEqual(previousDate.minusWeeks(1))) {
                        currentStreak++;
                    } else {
                        log.info("Current streak broken: user_id={}, habit_name={}, streak={}", getCurrentStreakDto.getUser().getId(), getCurrentStreakDto.getName(), currentStreak);
                        return currentStreak;
                    }
                    break;
            }
            previousDate = currentDate;
        }

        log.info("Current streak: user_id={}, habit_name={}, streak={}", getCurrentStreakDto.getUser().getId(), getCurrentStreakDto.getName(), currentStreak);
        return currentStreak;
    }

    @Override
    public double getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto) {
        if (getCompletionPercentageDto == null || getCompletionPercentageDto.getUser() == null || getCompletionPercentageDto.getName() == null || getCompletionPercentageDto.getStartDate() == null || getCompletionPercentageDto.getEndDate() == null) {
            log.error("Invalid input data for getting completion percentage: {}", getCompletionPercentageDto);
            throw new IllegalArgumentException("Invalid input data for getting completion percentage");
        }
        Habit habit = getHabitByName(getCompletionPercentageDto.getUser(), getCompletionPercentageDto.getName());
        if (Objects.isNull(habit)) {
            log.error("Habit not found: user_id={}, habit_name={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName());
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
            log.error("Wrong date in getCompletionPercentageDto: user_id={}, habit_name={}, start_date={}, end_date={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName(), startDate, endDate);
            throw new IllegalArgumentException("Wrong date in getCompletionPercentageDto");
        }

        double percentage = (double) completed / total * 100;
        log.info("Completion percentage: user_id={}, habit_name={}, percentage={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName(), percentage);
        return percentage;
    }

    @Override
    public UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto) {
        if (generateUserProgressReportDto == null || generateUserProgressReportDto.getUser() == null || generateUserProgressReportDto.getStartDate() == null || generateUserProgressReportDto.getEndDate() == null) {
            log.error("Invalid input data for generating user progress report: {}", generateUserProgressReportDto);
            throw new IllegalArgumentException("Invalid input data for generating user progress report");
        }
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

        UserProgressReportDto report = UserProgressReportDto.builder()
                .user(generateUserProgressReportDto.getUser())
                .habitProgresses(habitProgresses)
                .build();
        log.info("User progress report generated: user_id={}, report={}", generateUserProgressReportDto.getUser().getId(), report);
        return report;
    }

    @Override
    public Habit getHabitByName(User user, String name) {
        return habitRepository.getHabitByName(user, name);
    }
}