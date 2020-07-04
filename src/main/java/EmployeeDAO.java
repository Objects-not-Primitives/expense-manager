import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO {

    private static EmployeeDAO instance;
    private Connection connectWay;

    private EmployeeDAO(Connection connectWay) {
        this.connectWay = connectWay;
    }

    public static EmployeeDAO getInstance(Connection connectWay) {
        if (instance == null) {
            instance = new EmployeeDAO(connectWay);
        }
        return instance;
    }

    public boolean connect(String DB_URL, String USER, String PASS) {
        try {
            Connection con = DriverManager.getConnection(
                    DB_URL,
                    USER,
                    PASS);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateRecord(Employee employee) throws SQLException {
        String sqlCommand = "update employees set vacancy_name = ?, salary = ? where id = ?";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setString(1, employee.getVacancyName());
        preparedStatement.setInt(2, employee.getSalary());
        preparedStatement.setInt(3, employee.getId());
        int i = preparedStatement.executeUpdate();
    }

    public void deleteRecord(Employee employee) throws SQLException {
        String sqlCommand = "delete from employees where id = ?";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, employee.getId());
        int i = preparedStatement.executeUpdate();
    }

    public void insertRecord(Employee employee) throws SQLException {
        String sqlCommand = "insert into employees values (?,?,?)";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, employee.getId());
        preparedStatement.setString(2, employee.getVacancyName());
        preparedStatement.setInt(3, employee.getSalary());
        int i = preparedStatement.executeUpdate();
    }

    public Optional<Employee> selectOne(int id) throws SQLException {
        PreparedStatement preparedStatement = connectWay.prepareStatement("SELECT * FROM employees where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return Optional.of(new Employee(resultSet.getInt("id"), resultSet.getString("vacancy_name"), resultSet.getInt("salary")));
        }
        return Optional.empty();
    }

    public void createTable() throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "create table employees_new (id int primary key, vacancy_name char(100), salary int )";
        stmt.execute(sqlCommand);
    }

    public List<Employee> selectAll() throws SQLException {
        Statement stmt = connectWay.createStatement();
        List<Employee> employeesList = new ArrayList<Employee>();
        String sqlCommand = "SELECT * FROM employees ";
        ResultSet resSet = stmt.executeQuery(sqlCommand);
        while (resSet.next()) {
            employeesList.add(new Employee(resSet.getInt("id"), resSet.getString("vacancy_name"), resSet.getInt("salary")));
        }
        return employeesList;
    }
}
