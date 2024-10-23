package homework1;

import homework1.controller.MainController;
import homework1.model.utils.DatabaseConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
public class Main {
    public static void main(String[] args){
        try {
runLiquibase();
        } catch (Exception e) {
        log.warn("Run liquibase exception: {}", e.getMessage());
        }
        log.info("Run liquibase done");
        MainController controller = new MainController();
        controller.start();
    }

    private static void runLiquibase() throws Exception {
        Connection connection = DatabaseConnection.getConnection();

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        String changelogFile = "db/changelog/db.changelog-master.xml";

        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        Liquibase liquibase = new Liquibase(changelogFile, resourceAccessor, database);

        liquibase.update("");

        connection.close();
    }
}