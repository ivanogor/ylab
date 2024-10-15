package homework1.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
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
        System.out.println("19. Get all users [FOR ADMIN]");
        System.out.println("0. Exit");
    }

    public int getUserChoice() {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    public LocalDate getUserDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}