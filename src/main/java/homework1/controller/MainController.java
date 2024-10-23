package homework1.controller;

import homework1.model.entity.User;
import homework1.model.repository.HabitRepository;
import homework1.model.repository.UserRepository;
import homework1.model.repository.impl.HabitRepositoryImpl;
import homework1.model.repository.impl.UserRepositoryImpl;
import homework1.model.service.HabitService;
import homework1.model.service.UserService;
import homework1.model.service.impl.HabitServiceImpl;
import homework1.model.service.impl.UserServiceImpl;
import homework1.view.ConsoleView;

import java.util.Objects;

/**
 * Класс MainController является центральным контроллером для обработки взаимодействия с пользователем
 * и управления потоком приложения. Он координирует между представлением, сервисами и репозиториями
 * для выполнения различных операций, таких как регистрация пользователя, вход в систему, управление привычками
 * и генерация отчетов.
 */
public class MainController {
    private final ConsoleView view;
    private final UserController userController;
    private final HabitController habitController;

    /**
     * Конструирует новый экземпляр MainController и инициализирует необходимые сервисы
     * и компоненты представления.
     */
    public MainController() {
        UserRepository userRepository = new UserRepositoryImpl();
        HabitRepository habitRepository = new HabitRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        HabitService habitService = new HabitServiceImpl(habitRepository);
        this.view = new ConsoleView();
        this.userController = new UserController(userService);
        this.habitController = new HabitController(habitService);
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
                    case 1 -> currentUser = userController.registerUser();
                    case 2 -> currentUser = userController.loginUser();
                    case 3 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            userController.updateUser(currentUser);
                        }
                    }
                    case 4 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            userController.deleteUser(currentUser);
                        }
                    }
                    case 5 -> userController.resetPassword();
                    case 6 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            userController.blockUser(currentUser);
                        }
                    }
                    case 7 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            userController.unblockUser(currentUser);
                        }
                    }
                    case 8 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.createHabit(currentUser);
                        }
                    }
                    case 9 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.updateHabit(currentUser);
                        }
                    }
                    case 10 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.deleteHabit(currentUser);
                        }
                    }
                    case 11 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.getAllHabits(currentUser);
                        }
                    }
                    case 12 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.getHabitsByCreationDate(currentUser);
                        }
                    }
                    case 13 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.getHabitsByFrequency(currentUser);
                        }
                    }
                    case 14 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.markHabitAsCompleted(currentUser);
                        }
                    }
                    case 15 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.countHabitCompletionsForPeriod(currentUser);
                        }
                    }
                    case 16 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.getCurrentStreak(currentUser);
                        }
                    }
                    case 17 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.getCompletionPercentage(currentUser);
                        }
                    }
                    case 18 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            habitController.generateUserProgressReport(currentUser);
                        }
                    }
                    case 19 -> {
                        if (Objects.isNull(currentUser)) {
                            view.displayMessage("Please log in first.");
                        } else {
                            userController.getAllUsers(currentUser);
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
}