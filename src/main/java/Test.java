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
        /*EmployeeDAO employeeDAO = test.testConnect(property);
        employeeDAO.insertRecord(new Employee(6, "chert", 250000));//test
        employeeDAO.updateRecord(new Employee(2, "chert", 2500));//test
        employeeDAO.deleteRecord(new Employee(5, "chert", 250000));//test*/
    }

    private Properties loadProperties() throws IOException {
        Properties property = new Properties();
        property.load(new FileInputStream("src/main/resources/application.properties"));
        return property;
    }

    private void execScripts() throws IOException {
        SqlCommandLauncher sqlCommand = new SqlCommandLauncher();
        sqlCommand.launchSQLscript("src\\main\\resources\\deleteDB.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\createDB.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\initDB.sql");
    }

    private EmployeeDAO testConnect(Properties property) throws SQLException {
        Connection con = DriverManager.getConnection(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance(con);
        boolean connectionTest = employeeDAO.connect(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
        System.out.println(connectionTest);
        return employeeDAO;
    }
}
