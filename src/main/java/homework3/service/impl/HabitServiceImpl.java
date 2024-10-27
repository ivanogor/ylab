package homework3.service.impl;

import homework3.dto.*;
import homework3.entity.Habit;
import homework3.entity.User;
import homework3.exception.HabitAlreadyException;
import homework3.exception.HabitNotFoundException;
import homework3.mapper.HabitMapper;
import homework3.repository.HabitRepository;
import homework3.service.HabitService;
import homework3.utils.validators.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static homework3.utils.RequestUtils.getCurrentUser;

/**
 * Реализация интерфейса {@link HabitService} для управления привычками.
 */
@Slf4j
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public OperationResultDto createHabit(CreateHabitDto createHabitDto) {
        try {
            ValidationUtils.validate(createHabitDto);
            HabitDto habitDto = createHabitDto.getHabitDto();
            HttpServletRequest req = createHabitDto.getRequest();
            User currentUser = getCurrentUser(req);

            Habit habit = HabitMapper.INSTANCE.toEntity(habitDto);

            Habit createdHabit = habitRepository.createHabit(currentUser, habit);
            log.info("Habit creation result: {}", createdHabit);
            return createOperationResult(true, "Habit created successfully", createdHabit);
        } catch (HabitAlreadyException e) {
            return createOperationResult(false, "Habit already exists", null);
        } catch (Exception e) {
            log.warn("Error creating habit: " + e.getMessage(), e);
            return createOperationResult(false, "Internal server error", null);
        }
    }

    @Override
    public OperationResultDto updateHabit(UpdateHabitDto updateHabitDto) {
        ValidationUtils.validate(updateHabitDto);
        User currentUser = getCurrentUser(updateHabitDto.getRequest());
        Habit habit = getHabitByName(currentUser, updateHabitDto.getOldHabitName());
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        updateHabitFields(habit, updateHabitDto);
        boolean result = habitRepository.updateHabit(currentUser, habit);
        log.info("Habit update result: {}", result);
        return createOperationResult(result, result ? "Habit updated successfully" : "Failed to update habit", null);
    }

    @Override
    public OperationResultDto deleteHabit(DeleteHabitDto deleteHabitDto) {
        ValidationUtils.validate(deleteHabitDto);
        User currentUser = getCurrentUser(deleteHabitDto.getRequest());
        boolean result = habitRepository.deleteHabit(currentUser, deleteHabitDto.getName());
        log.info("Habit deletion result: {}", result);
        return createOperationResult(result, result ? "Habit deleted successfully" : "Failed to delete habit", null);
    }

    @Override
    public OperationResultDto getAllHabits(HttpServletRequest req) {
        User currentUser = getCurrentUser(req);
        ValidationUtils.validate(currentUser);
        Set<Habit> habits = habitRepository.getAllHabits(currentUser);
        log.info("All habits found for user: user_id={}, habits={}", currentUser.getId(), habits);
        return createOperationResult(true, "Habits retrieved successfully", habits);
    }

    @Override
    public OperationResultDto getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto) {
        ValidationUtils.validate(getHabitsByCreationDateDto);
        User currentUser = getCurrentUser(getHabitsByCreationDateDto.getRequest());
        Set<Habit> habits = filterHabitsByCreationDate(currentUser, getHabitsByCreationDateDto.getCreationDate());
        log.info("Habits found by creation date: user_id={}, creation_date={}, habits={}", currentUser.getId(), getHabitsByCreationDateDto.getCreationDate(), habits);
        return createOperationResult(true, "Habits by creation date retrieved successfully", habits);
    }

    @Override
    public OperationResultDto getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto) {
        ValidationUtils.validate(getHabitsByFrequencyDateDto);
        User currentUser = getCurrentUser(getHabitsByFrequencyDateDto.getRequest());
        Set<Habit> habits = filterHabitsByFrequency(currentUser, getHabitsByFrequencyDateDto.getFrequency());
        log.info("Habits found by frequency: user_id={}, frequency={}, habits={}", currentUser, getHabitsByFrequencyDateDto.getFrequency(), habits);
        return createOperationResult(true, "Habits by frequency retrieved successfully", habits);
    }

    @Override
    public OperationResultDto markHabitAsCompleted(CompletionHabitDto completionHabitDto) {
        ValidationUtils.validate(completionHabitDto);
        User currentUser = getCurrentUser(completionHabitDto.getRequest());
        Habit habit = getHabitByName(currentUser, completionHabitDto.getHabitName());
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        habit.getCompletions().add(completionHabitDto.getDate());
        habit.setCompleted(true);
        boolean result = habitRepository.updateHabit(currentUser, habit);
        log.info("Habit marked as completed: user_id={}, habit_name={}, result={}", currentUser.getId(), completionHabitDto.getHabitName(), result);
        return createOperationResult(result, result ? "Habit marked as completed successfully" : "Failed to mark habit as completed", null);
    }

    @Override
    public OperationResultDto countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto) {
        ValidationUtils.validate(countHabitCompletionsForPeriodDto);
        User currentUser = getCurrentUser(countHabitCompletionsForPeriodDto.getRequest());
        Habit habit = getHabitByName(currentUser, countHabitCompletionsForPeriodDto.getHabitName());
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        LocalDate now = LocalDate.now();
        LocalDate startDate = calculateStartDate(countHabitCompletionsForPeriodDto.getPeriod(), now);
        long count = habit.getCompletions().stream()
                .filter(cd -> cd.isAfter(startDate) || cd.isEqual(startDate))
                .filter(cd -> cd.isBefore(now) || cd.isEqual(now))
                .count();
        log.info("Habit completions count for period: user_id={}, habit_name={}, period={}, count={}", currentUser.getId(), countHabitCompletionsForPeriodDto.getHabitName(), countHabitCompletionsForPeriodDto.getPeriod(), count);
        return createOperationResult(true, "Habit completions count for period retrieved successfully", count);
    }

    @Override
    public OperationResultDto getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        ValidationUtils.validate(getCurrentStreakDto);
        User currentUser = getCurrentUser(getCurrentStreakDto.getRequest());
        Habit habit = getHabitByName(currentUser, getCurrentStreakDto.getName());
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        List<LocalDate> completions = new ArrayList<>(habit.getCompletions().stream().toList());
        if (completions.isEmpty()) {
            log.info("No completions found for habit: user_id={}, habit_name={}", currentUser.getId(), getCurrentStreakDto.getName());
            return createOperationResult(true, "No completions found for habit", 0);
        }
        completions.sort(LocalDate::compareTo);
        int streak = calculateCurrentStreak(completions, habit.getFrequency());
        log.info("Current streak: user_id={}, habit_name={}, streak={}", currentUser.getId(), getCurrentStreakDto.getName(), streak);
        return createOperationResult(true, "Current streak retrieved successfully", streak);
    }

    @Override
    public OperationResultDto getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto) {
        ValidationUtils.validate(getCompletionPercentageDto);
        User currentUser = getCurrentUser(getCompletionPercentageDto.getRequest());
        Habit habit = getHabitByName(currentUser, getCompletionPercentageDto.getName());
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        long total = calculateTotalPeriod(habit.getFrequency(), getCompletionPercentageDto.getStartDate(), getCompletionPercentageDto.getEndDate());
        long completed = habit.getCompletions().stream()
                .filter(date -> !date.isBefore(getCompletionPercentageDto.getStartDate()) && !date.isAfter(getCompletionPercentageDto.getEndDate()))
                .count();
        if (total == 0) {
            log.error("Wrong date in getCompletionPercentageDto: user_id={}, habit_name={}, start_date={}, end_date={}", currentUser.getId(), getCompletionPercentageDto.getName(), getCompletionPercentageDto.getStartDate(), getCompletionPercentageDto.getEndDate());
            throw new IllegalArgumentException("Wrong date in getCompletionPercentageDto");
        }
        double percentage = (double) completed / total * 100;
        log.info("Completion percentage: user_id={}, habit_name={}, percentage={}", currentUser.getId(), getCompletionPercentageDto.getName(), percentage);
        return createOperationResult(true, "Completion percentage retrieved successfully", percentage);
    }

    @Override
    public UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto) {
        ValidationUtils.validate(generateUserProgressReportDto);
        User currentUser = getCurrentUser(generateUserProgressReportDto.getRequest());
        Set<Habit> habits = habitRepository.getAllHabits(currentUser);
        List<UserProgressReportDto.HabitProgress> habitProgresses = habits.stream()
                .map(habit -> createHabitProgress(generateUserProgressReportDto, habit))
                .toList();

        UserProgressReportDto report = UserProgressReportDto.builder()
                .user(currentUser)
                .habitProgresses(habitProgresses)
                .build();
        log.info("User progress report generated: user_id={}, report={}", currentUser.getId(), report);
        return report;
    }

    private UserProgressReportDto.HabitProgress createHabitProgress(GenerateUserProgressReportDto generateUserProgressReportDto, Habit habit) {
        int streak = extractStreak(generateUserProgressReportDto, habit);
        double completionPercentage = extractCompletionPercentage(generateUserProgressReportDto);
        return UserProgressReportDto.HabitProgress.builder()
                .streak(streak)
                .completionPercentage(completionPercentage)
                .habit(habit)
                .build();
    }

    private int extractStreak(GenerateUserProgressReportDto generateUserProgressReportDto, Habit habit) {
        GetCurrentStreakDto getCurrentStreakDto = GetCurrentStreakDto.builder()
                .request(generateUserProgressReportDto.getRequest())
                .name(habit.getName())
                .build();
        OperationResultDto streakResult = getCurrentStreak(getCurrentStreakDto);
        return (int) streakResult.getData();
    }

    private double extractCompletionPercentage(GenerateUserProgressReportDto generateUserProgressReportDto) {
        GetCompletionPercentageDto getCompletionPercentageDto = GetCompletionPercentageDto.builder()
                .request(generateUserProgressReportDto.getRequest())
                .startDate(generateUserProgressReportDto.getStartDate())
                .endDate(generateUserProgressReportDto.getEndDate())
                .build();
        OperationResultDto completionPercentageResult = getCompletionPercentage(getCompletionPercentageDto);
        return (double) completionPercentageResult.getData();
    }

    @Override
    public Habit getHabitByName(User user, String name) {
        Habit habit = habitRepository.getHabitByName(user, name);
        if (habit == null) {
            throw new HabitNotFoundException();
        }
        return habit;
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

    private OperationResultDto createOperationResult(boolean success, String message, Object data) {
        return OperationResultDto.builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }

    private Set<Habit> filterHabitsByCreationDate(User user, LocalDate creationDate) {
        return habitRepository.getAllHabits(user)
                .stream()
                .filter(habit -> habit.getCreationDate().equals(creationDate))
                .collect(Collectors.toSet());
    }

    private Set<Habit> filterHabitsByFrequency(User user, Habit.Frequency frequency) {
        return habitRepository.getAllHabits(user)
                .stream()
                .filter(habit -> habit.getFrequency().equals(frequency))
                .collect(Collectors.toSet());
    }
}
