import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeService {
    private static final ObjectMapper mapper = new ObjectMapper();
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

    public void getOne(String id, HttpServletResponse resp) {
        try {
            Employee employee = employeeDAO.selectOne(Integer.parseInt(id)).orElse(null);
            if (employee == null) {
                servletWriter("There is no Employee with such id", resp);
            } else {
                servletWriter(employeeToJson(employee), resp);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void getAll(HttpServletResponse resp) {
        try {
            servletWriter(employeeDAO.selectAll().stream()
                    .map(EmployeeService::employeeToJson)
                    .collect(Collectors.joining(System.lineSeparator())), resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void post(String jsonString, HttpServletResponse resp) {
        try {
            employeeDAO.insertRecord(Objects.requireNonNull(jsonToEmployee(jsonString)));
            servletWriter("New Employee added", resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void put(String jsonString, HttpServletResponse resp) {
        try {
            employeeDAO.updateRecord(Objects.requireNonNull(jsonToEmployee(jsonString)));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void delete(int id, HttpServletResponse resp) {
        try {
            employeeDAO.deleteRecord(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void servletWriter(String text, HttpServletResponse resp) {
        try {
            resp.getWriter().println(text);
        } catch (IOException e) {
            System.out.println("Output problems");
            e.printStackTrace();
        }
    }

    private static String employeeToJson(Employee employee) {
        try {
            return mapper.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Problems encountered when processing (parsing, generating) JSON content";
        }
    }

    private static Employee jsonToEmployee(String jsonString) {
        try {
            return mapper.readValue(jsonString, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            return null;
        }
    }
}
