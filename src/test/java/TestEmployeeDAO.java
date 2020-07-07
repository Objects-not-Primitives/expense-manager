import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestEmployeeDAO {
    private static final String propertiesPath = "src\\main\\resources\\application.properties";
    private static final String deleteDBPath = "src\\main\\resources\\deleteDB.sql";
    private static final String createDBPath = "src\\main\\resources\\createDB.sql";

    public static void main(String[] args) throws SQLException, IOException {
        testConnect();
        testDAO();
    }

    private static boolean testDAO() throws SQLException, IOException {
        execScripts();
        Properties property = PropertyLoader.load(propertiesPath);
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
        if (testEmployeeList.get(0).equals(employeeDAO.selectOne(1).get()) && testEmployeeList.equals(testEmployeeListDB)) {
            System.out.println("Методы DAO работают нормально");
            return true;
        } else {
            System.out.println("Методы DAO не работают");
            return false;
        }
    }

    private static void execScripts() throws IOException {
        Properties properties = PropertyLoader.load(propertiesPath);
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        scriptExecutor.executeSQL(deleteDBPath, properties);
        scriptExecutor.executeSQL(createDBPath, properties);
    }

    private static boolean testConnect() throws IOException {
        Properties property = PropertyLoader.load(propertiesPath);
        try(Connection con = EmployeeDAO.connect(property)) {
            System.out.println("Подключение к БД установлено");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Подключение к БД отсутствует");
            return false;
        }
    }
}
