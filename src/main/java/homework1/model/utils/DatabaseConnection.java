package homework1.model.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Утилитарный класс для управления подключением к базе данных.
 * Он загружает параметры подключения из файла application.properties и предоставляет методы
 * для получения соединения с базой данных и настройки тестового подключения.
 */
@Slf4j
@UtilityClass
public class DatabaseConnection {

    private static String url;
    private static String username;
    private static String password;

    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (Exception e) {
            log.error("Failed to load application properties", e);
            throw new RuntimeException("Failed to load application properties", e);
        }
    }

    /**
     * Получает соединение с базой данных.
     *
     * @return Объект Connection для подключения к базе данных.
     * @throws SQLException если произошла ошибка при установлении соединения.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Устанавливает параметры подключения для тестовой базы данных.
     *
     * @param testUrl      URL тестовой базы данных.
     * @param testUsername Имя пользователя для тестовой базы данных.
     * @param testPassword Пароль для тестовой базы данных.
     */
    public static void setTestConnection(String testUrl, String testUsername, String testPassword) {
        url = testUrl;
        username = testUsername;
        password = testPassword;
    }
}