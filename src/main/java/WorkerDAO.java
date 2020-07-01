import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkerDAO {
    private static WorkerDAO instance;
    Connection connectWay;

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
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "UPDATE vacancies SET vacancyname= " + "\'" + worker.getVacancyName() + "\'" + ", salary=" + worker.getSalary() + " where id=" + worker.getId();
        int i = stmt.executeUpdate(sqlCommand);
    }

    public void deleteRecord(Worker worker) throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "delete from vacancies where id=" + worker.getId();
        int i = stmt.executeUpdate(sqlCommand);
    }

    public void insertRecord(Worker worker) throws SQLException {

        Statement stmt = connectWay.createStatement();
        String sqlCommand = "insert into vacancies values(" + worker.getId() + ", \'" + worker.getVacancyName() + "\'," + worker.getSalary() + ")";
        int i = stmt.executeUpdate(sqlCommand);
    }

    public Optional<Worker> selectOne(int id) throws SQLException {
        Statement stmt = connectWay.createStatement();

        String sqlCommand = "SELECT * FROM vacancies where id = " + id;
        ResultSet resSet = stmt.executeQuery(sqlCommand);
        while (resSet.next()) {
            return Optional.of(new Worker(resSet.getInt("id"), resSet.getString("vacancyname"), resSet.getInt("salary")));
        }
        return Optional.empty();
    }

    public void createTable() throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "create table test (id int, fieldInt int, fieldString char(10))";
        stmt.execute(sqlCommand);
    }

    public List<Worker> selectAll() throws SQLException {
        Statement stmt = connectWay.createStatement();
        List<Worker> workersList = new ArrayList<Worker>();
        String sqlCommand = "SELECT * FROM vacancies ";
        ResultSet resSet = stmt.executeQuery(sqlCommand);
        while (resSet.next()) {
            workersList.add(new Worker(resSet.getInt("id"), resSet.getString("vacancyname"), resSet.getInt("salary")));
        }
        return workersList;
    }
}
