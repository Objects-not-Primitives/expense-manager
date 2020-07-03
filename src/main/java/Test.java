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
        /*WorkerDAO workerDAO = test.testConnect(property);
        workerDAO.insertRecord(new Worker(6, "chert", 250000));//test
        workerDAO.updateRecord(new Worker(2, "chert", 2500));//test
        workerDAO.deleteRecord(new Worker(5, "chert", 250000));//test*/
    }

    private Properties loadProperties() throws IOException {
        Properties property = new Properties();
        property.load(new FileInputStream("src/main/resources/application.properties"));
        return property;
    }

    private void execScripts() throws IOException {
        SqlCommandLauncher sqlCommand = new SqlCommandLauncher();
        sqlCommand.launchSQLscript("src\\main\\resources\\deleteTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\createTables.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\initTables.sql");
    }

    private WorkerDAO testConnect(Properties property) throws SQLException {
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
        return workerDAO;
    }
}
