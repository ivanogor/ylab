package homework1.model.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * Утилитный класс для валидации различных данных.
 */
@UtilityClass
public class ValidationUtils {

    /**
     * Регулярное выражение для проверки валидности email-адресов.
     */
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

    /**
     * Проверяет, является ли email валидным.
     *
     * @param email Email для проверки.
     * @return true, если email валиден, иначе false.
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}