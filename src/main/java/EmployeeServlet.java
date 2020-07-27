import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/first-servlet/*"})
public class EmployeeServlet extends HttpServlet {
    private static final EmployeeService employeeService = new EmployeeService();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String id = (req.getParameter("id"));
            if (id.equals("")) {
                resp.getWriter().println(employeeService.getEmployeeDAO().selectAll().stream()
                        .map(EmployeeServlet::employeeToJson)
                        .collect(Collectors.joining(System.lineSeparator())));
            } else {Employee employee = employeeService.getEmployeeDAO().selectOne(Integer.parseInt(id)).orElse(null);
                if (employee == null){ resp.getWriter().println("There is no Employee with such id");
                } else{
                resp.getWriter().println(employeeToJson(employee));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            resp.getWriter().println("No connection to database");
        }
    }

    private static String employeeToJson(Employee employee) {
        try {
            return mapper.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            }
            return null;
        }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        try {
            employeeService.getEmployeeDAO().insertRecord(mapper.readValue(str, Employee.class));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            resp.getWriter().println("No connection to database");
        }
        resp.getWriter().println("Add new vacancy");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        try {
            employeeService.getEmployeeDAO().updateRecord(mapper.readValue(str, Employee.class));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            resp.getWriter().println("No connection to database");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            employeeService.getEmployeeDAO().deleteRecord(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            resp.getWriter().println("No connection to database");
        }
    }
}

