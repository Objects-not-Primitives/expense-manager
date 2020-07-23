import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/first-servlet/*"})
public class EmployeeServlet extends HttpServlet {
    private static final String propertiesPath = "src\\main\\resources\\application.properties";

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    private static EmployeeDAO addEmployeeDao() throws IOException, SQLException {
        Properties property = PropertyLoader.load(propertiesPath);
        return EmployeeDAO.getInstance(property);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            EmployeeDAO employeeDAO = addEmployeeDao();
            String id = (req.getParameter("id"));
            if (id == null) {
                resp.getWriter().println(employeeDAO.selectAll().stream().map(EmployeeServlet::transEmployeeToJson).collect(Collectors.joining(System.lineSeparator())));
            } else
                resp.getWriter().println(transEmployeeToJson(employeeDAO.selectOne(Integer.parseInt(id)).get()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static String transEmployeeToJson(Employee t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper mapper = new ObjectMapper();
        try {
            EmployeeDAO employeeDAO = addEmployeeDao();
            employeeDAO.insertRecord(mapper.readValue(str, Employee.class));
            resp.getWriter().println("Add new vacancy");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper mapper = new ObjectMapper();
        try {
            EmployeeDAO employeeDAO = addEmployeeDao();
            employeeDAO.updateRecord(mapper.readValue(str, Employee.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            EmployeeDAO employeeDAO = addEmployeeDao();
            employeeDAO.deleteRecord(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }
}

