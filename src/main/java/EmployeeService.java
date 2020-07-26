import java.io.IOException;
import java.sql.SQLException;

public class EmployeeService {
    private static final String propertiesPath = "application.properties";

    EmployeeDAO employeeDAO;

    public EmployeeService() {
        try {
            this.employeeDAO = EmployeeDAO.getInstance(PropertyLoader.load(propertiesPath));
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }
}
