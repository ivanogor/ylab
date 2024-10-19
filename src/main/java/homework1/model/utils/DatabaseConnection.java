package homework1.model.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void setTestConnection(String testUrl, String testUsername, String testPassword) {
        url = testUrl;
        username = testUsername;
        password = testPassword;
    }
}