package homework1.controller;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.service.HabitService;
import homework1.view.ConsoleView;

import java.time.LocalDate;
import java.util.Set;

/**
 * Класс HabitController отвечает за обработку всех операций, связанных с привычками.
 * Он координирует между представлением и сервисом привычек для выполнения операций,
 * таких как создание, обновление, удаление, получение и отметка привычек как выполненных.
 */
public class HabitController {
    private final HabitService habitService;
    private final ConsoleView view;

    /**
     * Конструирует новый экземпляр HabitController и инициализирует необходимые сервисы
     * и компоненты представления.
     *
     * @param habitService Сервис привычек.
     */
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
        this.view = new ConsoleView();
    }

    /**
     * Создает новую привычку для текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void createHabit(User currentUser) {
        String name = view.getUserInput("Enter habit name:");
        String description = view.getUserInput("Enter habit description:");
        Habit.Frequency frequency = getFrequencyInput("Enter habit frequency (DAILY/WEEKLY):");

        Habit habit = Habit.builder()
                .name(name)
                .description(description)
                .frequency(frequency)
                .creationDate(LocalDate.now())
                .isCompleted(false)
                .build();

        CreateHabitDto createHabitDto = new CreateHabitDto(currentUser, habit);
        habitService.createHabit(createHabitDto);
        view.displayMessage("Habit created successfully.");
    }

    /**
     * Обновляет существующую привычку для текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void updateHabit(User currentUser) {
        String oldHabitName = view.getUserInput("Enter old habit name:");
        String newHabitName = view.getUserInput("Enter new habit name (leave empty to skip):");
        newHabitName = newHabitName.isEmpty() ? null : newHabitName;
        String newHabitDescription = view.getUserInput("Enter new habit description (leave empty to skip):");
        newHabitDescription = newHabitDescription.isEmpty() ? null : newHabitDescription;
        Habit.Frequency newFrequency = getFrequencyInput("Enter new habit frequency (DAILY/WEEKLY, leave empty to skip):");

        UpdateHabitDto updateHabitDto = new UpdateHabitDto(currentUser, oldHabitName, newHabitName, newHabitDescription, newFrequency);
        habitService.updateHabit(updateHabitDto);
        view.displayMessage("Habit updated successfully.");
    }

    /**
     * Удаляет существующую привычку для текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void deleteHabit(User currentUser) {
        String habitName = view.getUserInput("Enter habit name to delete:");

        DeleteHabitDto deleteHabitDto = new DeleteHabitDto(currentUser, habitName);
        habitService.deleteHabit(deleteHabitDto);
        view.displayMessage("Habit deleted successfully.");
    }

    /**
     * Получает и отображает все привычки для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getAllHabits(User currentUser) {
        view.displayMessage("All habits:");
        Set<Habit> habits = habitService.getAllHabits(currentUser);
        if (habits.isEmpty()) {
            view.displayMessage("No habits found.");
        } else {
            habits.stream().map(Habit::toString).forEach(view::displayMessage);
        }
    }

    /**
     * Получает и отображает привычки, созданные в определенную дату, для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getHabitsByCreationDate(User currentUser) {
        LocalDate creationDate = view.getUserDateInput("Enter creation date (YYYY-MM-DD):");

        GetHabitsByCreationDateDto getHabitsByCreationDateDto = new GetHabitsByCreationDateDto(currentUser, creationDate);
        Set<Habit> habits = habitService.getHabitsByCreationDate(getHabitsByCreationDateDto);
        if (habits.isEmpty()) {
            view.displayMessage("No habits found for the given creation date.");
        } else {
            habits.stream().map(Habit::toString).forEach(view::displayMessage);
        }
    }

    /**
     * Получает и отображает привычки с определенной частотой для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getHabitsByFrequency(User currentUser) {
        Habit.Frequency frequency = getFrequencyInput("Enter frequency (DAILY/WEEKLY):");

        GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto = new GetHabitsByFrequencyDateDto(currentUser, frequency);
        Set<Habit> habits = habitService.getHabitsByFrequency(getHabitsByFrequencyDateDto);
        if (habits.isEmpty()) {
            view.displayMessage("No habits found for the given frequency.");
        } else {
            habits.stream().map(Habit::toString).forEach(view::displayMessage);
        }
    }

    /**
     * Отмечает привычку как выполненную для текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void markHabitAsCompleted(User currentUser) {
        String habitName = view.getUserInput("Enter habit name:");
        LocalDate completionDate = view.getUserDateInput("Enter completion date (YYYY-MM-DD):");

        CompletionHabitDto completionHabitDto = new CompletionHabitDto(currentUser, habitName, completionDate);
        habitService.markHabitAsCompleted(completionHabitDto);
        view.displayMessage("Habit marked as completed successfully.");
    }

    /**
     * Подсчитывает количество выполнений привычки за определенный период для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void countHabitCompletionsForPeriod(User currentUser) {
        String habitName = view.getUserInput("Enter habit name:");
        CountHabitCompletionsForPeriodDto.Period period = getPeriodInput();

        CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto = new CountHabitCompletionsForPeriodDto(currentUser, habitName, period);
        long count = habitService.countHabitCompletionsForPeriod(countHabitCompletionsForPeriodDto);
        view.displayMessage("Number of completions: " + count);
    }

    /**
     * Получает и отображает текущую серию выполнений для определенной привычки для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getCurrentStreak(User currentUser) {
        String habitName = view.getUserInput("Enter habit name:");

        GetCurrentStreakDto getCurrentStreakDto = new GetCurrentStreakDto(currentUser, habitName);
        int streak = habitService.getCurrentStreak(getCurrentStreakDto);
        view.displayMessage("Current streak: " + streak);
    }

    /**
     * Получает и отображает процент выполнения для определенной привычки за период для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void getCompletionPercentage(User currentUser) {
        String habitName = view.getUserInput("Enter habit name:");
        LocalDate startDate = view.getUserDateInput("Enter start date (YYYY-MM-DD):");
        LocalDate endDate = view.getUserDateInput("Enter end date (YYYY-MM-DD):");

        GetCompletionPercentageDto getCompletionPercentageDto = new GetCompletionPercentageDto(currentUser, habitName, startDate, endDate);
        double percentage = habitService.getCompletionPercentage(getCompletionPercentageDto);
        view.displayMessage("Completion percentage: " + percentage + "%");
    }

    /**
     * Генерирует и отображает отчет о прогрессе пользователя за определенный период для текущего пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    public void generateUserProgressReport(User currentUser) {
        LocalDate startDate = view.getUserDateInput("Enter start date (YYYY-MM-DD):");
        LocalDate endDate = view.getUserDateInput("Enter end date (YYYY-MM-DD):");

        GenerateUserProgressReportDto generateUserProgressReportDto = new GenerateUserProgressReportDto(currentUser, startDate, endDate);
        UserProgressReportDto report = habitService.generateUserProgressReport(generateUserProgressReportDto);
        view.displayMessage("User Progress Report:");
        report.getHabitProgresses().forEach(hp -> {
            view.displayMessage("Habit: " + hp.getHabit().getName());
            view.displayMessage("Streak: " + hp.getStreak());
            view.displayMessage("Completion Percentage: " + hp.getCompletionPercentage() + "%");
        });
    }

    /**
     * Вспомогательный метод для получения валидного значения частоты от пользователя.
     *
     * @param prompt Приглашение для отображения пользователю.
     * @return Валидное значение Habit.Frequency.
     */
    private Habit.Frequency getFrequencyInput(String prompt) {
        while (true) {
            try {
                String input = view.getUserInput(prompt).toUpperCase();
                input = input.isEmpty() ? null : input;
                return Habit.Frequency.valueOf(input);
            } catch (IllegalArgumentException e) {
                view.displayMessage("Invalid frequency. Please enter DAILY or WEEKLY.");
            }
        }
    }

    /**
     * Вспомогательный метод для получения валидного значения периода от пользователя.
     *
     * @return Валидное значение CountHabitCompletionsForPeriodDto.Period.
     */
    private CountHabitCompletionsForPeriodDto.Period getPeriodInput() {
        while (true) {
            try {
                String input = view.getUserInput("Enter period (DAY/WEEK/MONTH):").toUpperCase();
                return CountHabitCompletionsForPeriodDto.Period.valueOf(input);
            } catch (IllegalArgumentException e) {
                view.displayMessage("Invalid period. Please enter DAY, WEEK, or MONTH.");
            }
        }
    }
}