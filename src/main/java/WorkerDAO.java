import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkerDAO {

    private static WorkerDAO instance;
    private Connection connectWay;

    private WorkerDAO(Connection connectWay) {
        this.connectWay = connectWay;
    }

    public static WorkerDAO getInstance(Connection connectWay) {
        if (instance == null) {
            instance = new WorkerDAO(connectWay);
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

    public void updateRecord(Worker worker) throws SQLException {
        String sqlCommand = "update vacancies set vacancy_name = ?, salary = ? where id = ?";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setString(1, worker.getVacancyName());
        preparedStatement.setInt(2, worker.getSalary());
        preparedStatement.setInt(3, worker.getId());
        int i = preparedStatement.executeUpdate();
    }

    public void deleteRecord(int workerId) throws SQLException {
        String sqlCommand = "delete from vacancies where id = ?";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, workerId);
        int i = preparedStatement.executeUpdate();
    }

    public void insertRecord(Worker worker) throws SQLException {
        String sqlCommand = "insert into vacancies values (?,?,?)";
        PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, worker.getId());
        preparedStatement.setString(2, worker.getVacancyName());
        preparedStatement.setInt(3, worker.getSalary());
        int i = preparedStatement.executeUpdate();
    }

    public Optional<Worker> selectOne(int id) throws SQLException {
        PreparedStatement preparedStatement = connectWay.prepareStatement("SELECT * FROM vacancies where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return Optional.of(new Worker(resultSet.getInt("id"), resultSet.getString("vacancy_name"), resultSet.getInt("salary")));
        }
        return Optional.empty();
    }

    public List<Worker> selectAll() throws SQLException {
        Statement stmt = connectWay.createStatement();
        List<Worker> workersList = new ArrayList<Worker>();
        String sqlCommand = "SELECT * FROM vacancies ";
        ResultSet resSet = stmt.executeQuery(sqlCommand);
        while (resSet.next()) {
            workersList.add(new Worker(resSet.getInt("id"), resSet.getString("vacancy_name"), resSet.getInt("salary")));
        }
        return workersList;
    }
}
