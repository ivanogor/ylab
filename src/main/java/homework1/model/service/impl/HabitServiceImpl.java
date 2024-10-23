package homework1.model.service.impl;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.exception.HabitNotFoundException;
import homework1.model.repository.HabitRepository;
import homework1.model.service.HabitService;
import homework1.model.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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
        ValidationUtils.validateCreateHabitDto(createHabitDto);
        Habit result = habitRepository.createHabit(createHabitDto.getUser(), createHabitDto.getHabit());
        log.info("Habit creation result: {}", result);
        return Objects.nonNull(result);
    }

    @Override
    public boolean updateHabit(UpdateHabitDto updateHabitDto) {
        ValidationUtils.validateUpdateHabitDto(updateHabitDto);
        Habit habit = getHabitByName(updateHabitDto.getUser(), updateHabitDto.getOldHabitName());
        updateHabitFields(habit, updateHabitDto);
        boolean result = habitRepository.updateHabit(updateHabitDto.getUser(), habit);
        log.info("Habit update result: {}", result);
        return result;
    }

    @Override
    public boolean deleteHabit(DeleteHabitDto deleteHabitDto) {
        ValidationUtils.validateDeleteHabitDto(deleteHabitDto);
        boolean result = habitRepository.deleteHabit(deleteHabitDto.getUser(), deleteHabitDto.getName());
        log.info("Habit deletion result: {}", result);
        return result;
    }

    @Override
    public Set<Habit> getAllHabits(User user) {
        ValidationUtils.validateUser(user);
        Set<Habit> habits = habitRepository.getAllHabits(user);
        log.info("All habits found for user: user_id={}, habits={}", user.getId(), habits);
        return habits;
    }

    @Override
    public Set<Habit> getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        ValidationUtils.validateGetHabitsByCreationDateDto(getHabitsByCreationDateDto);
        Set<Habit> habits = habitRepository.getAllHabits(getHabitsByCreationDateDto.getUser())
                .stream()
                .filter(habit -> habit.getCreationDate().equals(getHabitsByCreationDateDto.getCreationDate()))
                .collect(Collectors.toUnmodifiableSet());
        log.info("Habits found by creation date: user_id={}, creation_date={}, habits={}", getHabitsByCreationDateDto.getUser().getId(), getHabitsByCreationDateDto.getCreationDate(), habits);
        return habits;
    }

    @Override
    public Set<Habit> getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        ValidationUtils.validateGetHabitsByFrequencyDateDto(getHabitsByFrequencyDateDto);
        Set<Habit> habits = habitRepository.getAllHabits(getHabitsByFrequencyDateDto.getUser())
                .stream()
                .filter(habit -> habit.getFrequency().equals(getHabitsByFrequencyDateDto.getFrequency()))
                .collect(Collectors.toUnmodifiableSet());
        log.info("Habits found by frequency: user_id={}, frequency={}, habits={}", getHabitsByFrequencyDateDto.getUser().getId(), getHabitsByFrequencyDateDto.getFrequency(), habits);
        return habits;
    }

    @Override
    public boolean markHabitAsCompleted(CompletionHabitDto completionHabitDto) {
        ValidationUtils.validateCompletionHabitDto(completionHabitDto);
        Habit habit = getHabitByName(completionHabitDto.getUser(), completionHabitDto.getHabitName());
        habit.getCompletions().add(completionHabitDto.getDate());
        habit.setCompleted(true);
        boolean result = habitRepository.updateHabit(completionHabitDto.getUser(), habit);
        log.info("Habit marked as completed: user_id={}, habit_name={}, result={}", completionHabitDto.getUser().getId(), completionHabitDto.getHabitName(), result);
        return result;
    }

    @Override
    public long countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        ValidationUtils.validateCountHabitCompletionsForPeriodDto(countHabitCompletionsForPeriodDto);
        Habit habit = getHabitByName(countHabitCompletionsForPeriodDto.getUser(), countHabitCompletionsForPeriodDto.getHabitName());
        LocalDate now = LocalDate.now();
        LocalDate startDate = calculateStartDate(countHabitCompletionsForPeriodDto.getPeriod(), now);
        long count = habit.getCompletions().stream()
                .filter(cd -> cd.isAfter(startDate) || cd.isEqual(startDate))
                .filter(cd -> cd.isBefore(now) || cd.isEqual(now))
                .count();
        log.info("Habit completions count for period: user_id={}, habit_name={}, period={}, count={}", countHabitCompletionsForPeriodDto.getUser().getId(), countHabitCompletionsForPeriodDto.getHabitName(), countHabitCompletionsForPeriodDto.getPeriod(), count);
        return count;
    }

    @Override
    public int getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        ValidationUtils.validateGetCurrentStreakDto(getCurrentStreakDto);
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
        return calculateCurrentStreak(completions, habit.getFrequency());
    }

    @Override
    public double getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto) {
        ValidationUtils.validateGetCompletionPercentageDto(getCompletionPercentageDto);
        Habit habit = getHabitByName(getCompletionPercentageDto.getUser(), getCompletionPercentageDto.getName());
        if (Objects.isNull(habit)) {
            log.error("Habit not found: user_id={}, habit_name={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName());
            throw new HabitNotFoundException();
        }
        long total = calculateTotalPeriod(habit.getFrequency(), getCompletionPercentageDto.getStartDate(), getCompletionPercentageDto.getEndDate());
        long completed = habit.getCompletions().stream()
                .filter(date -> !date.isBefore(getCompletionPercentageDto.getStartDate()) && !date.isAfter(getCompletionPercentageDto.getEndDate()))
                .count();
        if (total == 0) {
            log.error("Wrong date in getCompletionPercentageDto: user_id={}, habit_name={}, start_date={}, end_date={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName(), getCompletionPercentageDto.getStartDate(), getCompletionPercentageDto.getEndDate());
            throw new IllegalArgumentException("Wrong date in getCompletionPercentageDto");
        }
        double percentage = (double) completed / total * 100;
        log.info("Completion percentage: user_id={}, habit_name={}, percentage={}", getCompletionPercentageDto.getUser().getId(), getCompletionPercentageDto.getName(), percentage);
        return percentage;
    }

    @Override
    public UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto) {
        ValidationUtils.validateGenerateUserProgressReportDto(generateUserProgressReportDto);
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

    private void updateHabitFields(Habit habit, UpdateHabitDto updateHabitDto) {
        if (Objects.nonNull(updateHabitDto.getNewHabitName())) {
            habit.setName(updateHabitDto.getNewHabitName());
        }
        if (Objects.nonNull(updateHabitDto.getNewHabitDescription())) {
            habit.setDescription(updateHabitDto.getNewHabitDescription());
        }
        if (Objects.nonNull(updateHabitDto.getNewFrequency())) {
            habit.setFrequency(updateHabitDto.getNewFrequency());
        }
    }

    private LocalDate calculateStartDate(CountHabitCompletionsForPeriodDto.Period period, LocalDate now) {
        return switch (period) {
            case DAY -> now.minusDays(1);
            case WEEK -> now.minusWeeks(1);
            case MONTH -> now.minusMonths(1);
        };
    }

    private int calculateCurrentStreak(List<LocalDate> completions, Habit.Frequency frequency) {
        int currentStreak = 1;
        LocalDate previousDate = completions.get(completions.size() - 1);

        for (int i = completions.size() - 2; i >= 0; i--) {
            LocalDate currentDate = completions.get(i);
            if (isStreakBroken(currentDate, previousDate, frequency)) {
                return currentStreak;
            }
            currentStreak++;
            previousDate = currentDate;
        }
        return currentStreak;
    }

    private boolean isStreakBroken(LocalDate currentDate, LocalDate previousDate, Habit.Frequency frequency) {
        return switch (frequency) {
            case DAILY -> !currentDate.isEqual(previousDate.minusDays(1));
            case WEEKLY -> !currentDate.isEqual(previousDate.minusWeeks(1));
        };
    }

    private long calculateTotalPeriod(Habit.Frequency frequency, LocalDate startDate, LocalDate endDate) {
        return switch (frequency) {
            case DAILY -> startDate.datesUntil(endDate.plusDays(1)).count();
            case WEEKLY -> startDate.datesUntil(endDate.plusDays(1))
                    .filter(date -> date.getDayOfWeek() == startDate.getDayOfWeek()).count();
        };
    }
}