import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestEmployeeDAO {
    private static final String propertiesPath = "src\\main\\resources\\application.properties";

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println(testConnect() + ", " + testDAO());
    }

    private static boolean testDAO() throws SQLException, IOException {
        execScripts();
        Properties property = PropertyLoader.loadProperties(propertiesPath);
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance(property);

        Employee testEmployee1 = new Employee(1, "boss", 250000);
        Employee testEmployee2 = new Employee(2, "programmer", 60000);
        Employee testEmployee2New = new Employee(2, "programmer", 80000);
        Employee testEmployee3deleted = new Employee(3, "cleaner", 15000);

        List<Employee> testEmployeeList = new ArrayList<>();
        testEmployeeList.add(testEmployee1);
        testEmployeeList.add(testEmployee2New);
        employeeDAO.insertRecord(testEmployee1);
        employeeDAO.insertRecord(testEmployee2);
        employeeDAO.insertRecord(testEmployee3deleted);
        employeeDAO.updateRecord(testEmployee2New);
        employeeDAO.deleteRecord(testEmployee3deleted.getId());

        List<Employee> testEmployeeListDB = employeeDAO.selectAll();
        return testEmployeeList.get(0).equals(employeeDAO.selectOne(1).get()) && testEmployeeList.equals(testEmployeeListDB);
    }

    private static void execScripts() throws IOException {
        SqlCommandLauncher sqlCommand = new SqlCommandLauncher();
        sqlCommand.launchSQLscript("src\\main\\resources\\deleteDB.sql");
        sqlCommand.launchSQLscript("src\\main\\resources\\createDB.sql");
    }

    private static boolean testConnect() throws IOException, SQLException {
        Properties property = PropertyLoader.loadProperties(propertiesPath);
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance(property);
        return employeeDAO.connect(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
    }

}
