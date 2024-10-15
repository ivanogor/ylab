package homework1.model.utils;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Утилитный класс для хеширования паролей.
 * Использует алгоритм SHA-256 для хеширования паролей.
 */
@UtilityClass
public class PasswordHasher {

    /**
     * Хеширует пароль с использованием алгоритма SHA-256.
     *
     * @param password Пароль для хеширования.
     * @return Хешированный пароль в виде строки.
     * @throws RuntimeException если алгоритм хеширования недоступен.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    /**
     * Преобразует массив байтов в шестнадцатеричную строку.
     *
     * @param hash Массив байтов для преобразования.
     * @return Шестнадцатеричная строка.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}