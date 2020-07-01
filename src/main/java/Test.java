import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws SQLException, IOException {

        // loadProp()
        Properties property = new Properties();
        property.load(new FileInputStream("src/main/resources/application.properties"));

        // execScripts()
        SqlCommandLauncher sqlCommand = new SqlCommandLauncher();
        sqlCommand.launchSQLscript("src\\main\\resources\\deleteTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\createTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\initTables.sql");

        // testConnect()
        Worker cleaner = new Worker(1, "cleaner", 350);
        Connection con = DriverManager.getConnection(property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
        WorkerDAO workerDAO = WorkerDAO.getInstance(con);
        boolean connectionTest = workerDAO.connect(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
        System.out.println(connectionTest);
    }
}
