package homework1.controller;

import homework1.model.dto.*;
import homework1.model.entity.Habit;
import homework1.model.entity.User;
import homework1.model.repository.HabitRepository;
import homework1.model.repository.impl.HabitRepositoryImpl;
import homework1.model.repository.impl.UserRepositoryImpl;
import homework1.model.service.HabitService;
import homework1.model.service.UserService;
import homework1.model.service.impl.HabitServiceImpl;
import homework1.model.service.impl.UserServiceImpl;
import homework1.model.repository.UserRepository;
import homework1.view.ConsoleView;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Класс MainController является центральным контроллером для обработки взаимодействия с пользователем
 * и управления потоком приложения. Он координирует между представлением, сервисами и репозиториями
 * для выполнения различных операций, таких как регистрация пользователя, вход в систему, управление привычками
 * и генерация отчетов.
 */
public class MainController {
    private final UserService userService;
    private final HabitService habitService;
    private final ConsoleView view;

    /**
     * Конструирует новый экземпляр MainController и инициализирует необходимые сервисы
     * и компоненты представления.
     */
    public MainController() {
        UserRepository userRepository = new UserRepositoryImpl();
        HabitRepository habitRepository = new HabitRepositoryImpl();
        this.userService = new UserServiceImpl(userRepository);
        this.habitService = new HabitServiceImpl(habitRepository);
        this.view = new ConsoleView();
    }

    /**
     * Запускает приложение, отображая главное меню и обрабатывая выбор пользователя.
     * Метод работает в цикле до тех пор, пока пользователь не решит выйти из приложения.
     */
    public void start() {
        User currentUser = null;

        while (true) {
            view.displayMenu();
            int choice = view.getUserChoice();

            try {
                switch (choice) {
                    case 1 -> currentUser = registerUser();
                    case 2 -> currentUser = loginUser();
                    case 3 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            updateUser(currentUser);
                        }
                    }
                    case 4 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            deleteUser(currentUser);
                        }
                    }
                    case 5 -> resetPassword();
                    case 6 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            blockUser(currentUser);
                        }
                    }
                    case 7 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            unblockUser(currentUser);
                        }
                    }
                    case 8 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            createHabit(currentUser);
                        }
                    }
                    case 9 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            updateHabit(currentUser);
                        }
                    }
                    case 10 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            deleteHabit(currentUser);
                        }
                    }
                    case 11 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getAllHabits(currentUser);
                        }
                    }
                    case 12 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getHabitsByCreationDate(currentUser);
                        }
                    }
                    case 13 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getHabitsByFrequency(currentUser);
                        }
                    }
                    case 14 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            markHabitAsCompleted(currentUser);
                        }
                    }
                    case 15 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            countHabitCompletionsForPeriod(currentUser);
                        }
                    }
                    case 16 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getCurrentStreak(currentUser);
                        }
                    }
                    case 17 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getCompletionPercentage(currentUser);
                        }
                    }
                    case 18 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            generateUserProgressReport(currentUser);
                        }
                    }
                    case 19 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            getAllUsers(currentUser);
                        }
                    }
                    case 0 -> {
                        view.displayMessage("Exiting...");
                        return;
                    }
                    default -> view.displayMessage("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                view.displayMessage("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Получает и отображает всех пользователей в системе.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void getAllUsers(User currentUser) {
        List<User> users = userService.getAllUsers(currentUser);
        System.out.println("All users:");
        users.forEach(System.out::println);
    }

    /**
     * Регистрирует нового пользователя, собирая ввод пользователя и создавая новый объект User.
     *
     * @return Новый зарегистрированный объект User.
     */
    private User registerUser() {
        String name = view.getUserInput("Enter name:");
        String email = view.getUserInput("Enter email:");
        String password = view.getUserInput("Enter password:");

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(User.Role.USER)
                .isBlocked(false)
                .build();

        userService.register(user);
        view.displayMessage("User registered successfully.");
        return user;
    }

    /**
     * Входит в систему пользователя, собирая email и пароль и проверяя учетные данные.
     *
     * @return Объект User, если вход выполнен успешно, иначе null.
     */
    private User loginUser() {
        String email = view.getUserInput("Enter email:");
        String password = view.getUserInput("Enter password:");

        LoginDto loginDto = new LoginDto(email, password);
        User user = userService.login(loginDto);
        view.displayMessage("Logged in successfully.");
        return user;
    }

    /**
     * Обновляет информацию текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void updateUser(User currentUser) {
        String newName = view.getUserInput("Enter new name (leave empty to skip):");
        String newEmail = view.getUserInput("Enter new email (leave empty to skip):");
        String newPassword = view.getUserInput("Enter new password (leave empty to skip):");

        UpdateUserDto updateUserDto = new UpdateUserDto(currentUser.getEmail(), newName, newEmail, newPassword);
        userService.update(updateUserDto);
        view.displayMessage("User updated successfully.");
    }

    /**
     * Удаляет пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void deleteUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to delete:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.delete(userActionRequestDto);
        view.displayMessage("User deleted successfully.");
    }

    /**
     * Сбрасывает пароль пользователя на основе предоставленного email и нового пароля.
     */
    private void resetPassword() {
        String email = view.getUserInput("Enter email:");
        String newPassword = view.getUserInput("Enter new password:");

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto(email, newPassword);
        userService.resetPassword(resetPasswordDto);
        view.displayMessage("Password reset successfully.");
    }

    /**
     * Блокирует пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void blockUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to block:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.blockUser(userActionRequestDto);
        view.displayMessage("User blocked successfully.");
    }

    /**
     * Разблокирует пользователя на основе предоставленного email.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void unblockUser(User currentUser) {
        String email = view.getUserInput("Enter email of the user to unblock:");

        UserActionRequestDto userActionRequestDto = new UserActionRequestDto(currentUser, email);
        userService.unblockUser(userActionRequestDto);
        view.displayMessage("User unblocked successfully.");
    }

    /**
     * Создает новую привычку для текущего пользователя на основе ввода пользователя.
     *
     * @param currentUser Текущий вошедший пользователь.
     */
    private void createHabit(User currentUser) {
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
    private void updateHabit(User currentUser) {
        String oldHabitName = view.getUserInput("Enter old habit name:");
        String newHabitName = view.getUserInput("Enter new habit name (leave empty to skip):");
        newHabitName = newHabitName.isEmpty() ? null : newHabitName;
        String newHabitDescription = view.getUserInput("Enter new habit description (leave empty to skip):");
        newHabitDescription = newHabitDescription.isEmpty() ? null : newHabitName;
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
    private void deleteHabit(User currentUser) {
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
    private void getAllHabits(User currentUser) {
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
    private void getHabitsByCreationDate(User currentUser) {
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
    private void getHabitsByFrequency(User currentUser) {
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
    private void markHabitAsCompleted(User currentUser) {
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
    private void countHabitCompletionsForPeriod(User currentUser) {
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
    private void getCurrentStreak(User currentUser) {
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
    private void getCompletionPercentage(User currentUser) {
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
    private void generateUserProgressReport(User currentUser) {
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