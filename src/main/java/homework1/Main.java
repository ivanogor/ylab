package homework1;

import homework1.command.*;
import homework1.repository.UserRepository;
import homework1.service.HabitService;
import homework1.service.UserService;
import homework1.service.impl.HabitServiceImpl;
import homework1.service.impl.UserServiceImpl;
import homework1.utils.UserHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final UserRepository userRepository = new UserRepository();
    private static final UserService userService = new UserServiceImpl(userRepository);
    private static final HabitService habitService = new HabitServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserHolder userHolder = new UserHolder();
        Map<Integer, Command> commands = new HashMap<>();

        commands.put(1, new RegisterUserCommand(userService, scanner) {
            @Override
            public void execute() {
                super.execute();
                userHolder.setUser(getCurrentUser());
            }
        });
        commands.put(2, new LoginUserCommand(userService, scanner) {
            @Override
            public void execute() {
                super.execute();
                userHolder.setUser(getCurrentUser());
            }
        });
        commands.put(3, new UpdateUserCommand(userService, scanner, userHolder));
        commands.put(4, new DeleteUserCommand(userService, scanner, userHolder));
        commands.put(5, new ResetPasswordCommand(userService, scanner));
        commands.put(6, new BlockUserCommand(userService, scanner, userHolder));
        commands.put(7, new UnblockUserCommand(userService, scanner, userHolder));
        commands.put(8, new CreateHabitCommand(habitService, scanner, userHolder));
        commands.put(9, new UpdateHabitCommand(habitService, scanner, userHolder));
        commands.put(10, new DeleteHabitCommand(habitService, scanner, userHolder));
        commands.put(11, new GetAllHabitsCommand(habitService, userHolder));
        commands.put(12, new GetHabitsByCreationDateCommand(habitService, scanner, userHolder));
        commands.put(13, new GetHabitsByFrequencyCommand(habitService, scanner, userHolder));
        commands.put(14, new MarkHabitAsCompletedCommand(habitService, scanner, userHolder));
        commands.put(15, new CountHabitCompletionsForPeriodCommand(habitService, scanner, userHolder));
        commands.put(16, new GetCurrentStreakCommand(habitService, scanner, userHolder));
        commands.put(17, new GetCompletionPercentageCommand(habitService, scanner, userHolder));
        commands.put(18, new GenerateUserProgressReportCommand(habitService, scanner, userHolder));
        commands.put(19, new GetAllUsersCommand(userService, userHolder));

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Reset Password");
            System.out.println("6. Block User");
            System.out.println("7. Unblock User");
            System.out.println("8. Create Habit");
            System.out.println("9. Update Habit");
            System.out.println("10. Delete Habit");
            System.out.println("11. Get All Habits");
            System.out.println("12. Get Habits By Creation Date");
            System.out.println("13. Get Habits By Frequency");
            System.out.println("14. Mark Habit As Completed");
            System.out.println("15. Count Habit Completions For Period");
            System.out.println("16. Get Current Streak");
            System.out.println("17. Get Completion Percentage");
            System.out.println("18. Generate User Progress Report");
            System.out.println("19. Get All Users (Admin only)");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice == 0) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}