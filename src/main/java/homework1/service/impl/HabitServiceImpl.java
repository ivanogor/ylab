package homework1.service.impl;

import homework1.dto.*;
import homework1.entity.Habit;
import homework1.entity.User;
import homework1.exception.HabitNotFoundException;
import homework1.service.HabitService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис для управления привычками пользователя.
 * Предоставляет методы для создания, обновления, удаления привычек,
 * а также для получения статистики и отчетов о прогрессе.
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

        if (Objects.nonNull(updateHabitDto.getNewHabitName())) {
            habit.setName(updateHabitDto.getNewHabitName());
        }

        if (Objects.nonNull(updateHabitDto.getNewHabitDescription())) {
            habit.setDescription(updateHabitDto.getNewHabitDescription());
        }

        if (Objects.nonNull(updateHabitDto.getNewFrequency())) {
            habit.setFrequency(updateHabitDto.getNewFrequency());
        }

        updateHabitDto.getUser().getHabits().add(habit);
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
    public List<Habit> getAllHabits(User user) {
        return user.getHabits();
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
        habit.getCompletions().add(new Habit.HabitCompletion(completionHabitDto.getDate(), true));
        return true;
    }

    @Override
    public long generateHabitStatistics(GenerateHabitStatisticsDto generateHabitStatisticsDto) {
        Habit habit = getHabitByName(generateHabitStatisticsDto.getUser(), generateHabitStatisticsDto.getHabitName());
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (generateHabitStatisticsDto.getPeriod()) {
            case DAY -> {
                startDate = now.minusDays(1);
            }
            case WEEK -> {
                startDate = now.minusWeeks(1);
            }
            case MONTH -> {
                startDate = now.minusMonths(1);
            }
            default ->
                    throw new IllegalArgumentException("Unsupported period: " + generateHabitStatisticsDto.getPeriod());
        }

        return habit.getCompletions().stream()
                .filter(hb -> {
                    LocalDate completionDate = hb.getCompletionDate();
                    return completionDate.isAfter(startDate) || completionDate.isEqual(startDate);
                })
                .filter(h -> {
                    LocalDate completionDate = h.getCompletionDate();
                    return completionDate.isBefore(now) || completionDate.isEqual(now);
                })
                .count();
    }

    @Override
    public int getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto) {
        Habit habit = getHabitByName(getCurrentStreakDto.getUser(), getCurrentStreakDto.getName());

        if (Objects.isNull(habit)) {
            throw new HabitNotFoundException();
        }

        List<Habit.HabitCompletion> completions = habit.getCompletions();

        if (Objects.isNull(completions) || completions.isEmpty()) {
            return 0;
        }

        completions.sort(Comparator.comparing(Habit.HabitCompletion::getCompletionDate));

        int streak = 1;
        LocalDate previousDate = completions.getFirst().getCompletionDate();

        for (int i = 1; i < completions.size(); i++) {
            LocalDate currentDate = completions.get(i).getCompletionDate();

            switch (habit.getFrequency()){
                case DAILY -> {
                    if (currentDate.isEqual(previousDate.plusDays(1))){
                        streak++;
                    }
                }
                case WEEKLY -> {
                    if (currentDate.isEqual(previousDate.plusWeeks(1))){
                        streak++;
                    }
                }
            }
            if(!currentDate.isEqual(previousDate)){
                break;
            }
            previousDate = currentDate;
        }

        return streak;
    }

    @Override
    public double getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto){
        Habit habit = getHabitByName(getCompletionPercentageDto.getUser(), getCompletionPercentageDto.getName());
        if (Objects.isNull(habit)) {
            throw new HabitNotFoundException();
        }

        long total = 0;
        long completed = 0;
        LocalDate startDate = getCompletionPercentageDto.getStartDate();
        LocalDate endDate = getCompletionPercentageDto.getEndDate();
        List<Habit.HabitCompletion> habitCompletions = habit.getCompletions();
        switch (habit.getFrequency()){
            case DAILY -> {
                total = getCompletionPercentageDto.getStartDate()
                        .datesUntil(getCompletionPercentageDto.getEndDate().plusDays(1)).count();
                completed = habitCompletions.stream()
                        .filter(h -> {
                            LocalDate date = h.getCompletionDate();
                            return !date.isBefore(getCompletionPercentageDto.getStartDate()) &&
                                    !date.isAfter(getCompletionPercentageDto.getEndDate());
                        }).count();
            }
            case WEEKLY -> {
                total = startDate.datesUntil(endDate.plusDays(1))
                        .filter(date -> date.getDayOfWeek() == startDate.getDayOfWeek())
                        .count();
                completed = habitCompletions.stream()
                        .filter(h -> {
                            LocalDate date = h.getCompletionDate();
                            return !date.isBefore(getCompletionPercentageDto.getStartDate()) && !date.isAfter(getCompletionPercentageDto.getEndDate())
                                    && date.getDayOfWeek() == startDate.getDayOfWeek();
                        }).count();
            }
        }

        if(total == 0){
            throw new IllegalArgumentException("Wrong date in getCompletionPercentageDto");
        }

        return (double) completed / total * 100;
    }

    @Override
    public UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto){
        List<Habit> habits = generateUserProgressReportDto.getUser().getHabits();
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
