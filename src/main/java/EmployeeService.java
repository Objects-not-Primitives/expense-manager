import java.sql.SQLException;

public class EmployeeService {
    private static final String propertiesPath = "application.properties";

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        try {
            this.employeeDAO = EmployeeDAO.getInstance(PropertyLoader.load(propertiesPath));
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("No connection to database");
        }
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }
}
