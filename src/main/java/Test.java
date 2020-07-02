import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws SQLException, IOException {
        Test test = new Test();
        Properties property = test.loadProperties();
        test.execScripts();
        test.testConnect(property);
    }

    public Properties loadProperties() throws IOException {
        Properties property = new Properties();
        property.load(new FileInputStream("src/main/resources/application.properties"));
        return property;
    }

    public void execScripts() throws IOException {
        SqlCommandLauncher sqlCommand = new SqlCommandLauncher();
        sqlCommand.launchSQLscript("src\\main\\resources\\deleteTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\createTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\initTables.sql");
    }

    public void testConnect(Properties property) throws SQLException {
        Connection con = DriverManager.getConnection(
                property.getProperty("db.url"),
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
