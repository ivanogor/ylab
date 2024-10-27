package homework3.listener;

import homework3.utils.DatabaseConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@WebListener
@Slf4j
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application initialized");
        try {
            runLiquibase();
        } catch (Exception e) {
            log.error("Run liquibase exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application destroyed");
    }

    private static void runLiquibase() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            String changelogFile = "db/changelog/db.changelog-master.xml";
            ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
            Liquibase liquibase = new Liquibase(changelogFile, resourceAccessor, database);
            liquibase.update("");
        } catch (LiquibaseException e) {
            log.error("Error running Liquibase", e);
            throw e;
        }
    }
}