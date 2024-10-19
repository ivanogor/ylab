package homework1;

import homework1.model.utils.PasswordHasher;

public class HashPass {
    public static void main(String[] args) {
        String password1 = "password123";
        String password2 = "password456";

        String hashedPassword1 = PasswordHasher.hashPassword(password1);
        String hashedPassword2 = PasswordHasher.hashPassword(password2);

        System.out.println("password123: " + hashedPassword1);
        System.out.println("password456: " + hashedPassword2);
    }
}