import java.sql.*;

public class WorkerDAO {
    Connection connectWay;

    public WorkerDAO(Connection connectWay) {
        this.connectWay = connectWay;
    }

    public boolean connect (String DB_URL, String USER, String PASS){
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

    public void updateRecord (Worker worker) throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "UPDATE vacancies SET vacancyname= "+ "\'"+worker.getVacancyName()+ "\'" + ", salary="+worker.getSalary() +" where id="+ worker.getId();
        int i = stmt.executeUpdate(sqlCommand);
    }
    public void deleteRecord (Worker worker) throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "delete from vacancies where id="+ worker.getId();
        int i = stmt.executeUpdate(sqlCommand);
    }
    public void insertRecord (Worker worker) throws SQLException {

        Statement stmt = connectWay.createStatement();
        String sqlCommand = "insert into vacancies values("+worker.getId()+", \'"+worker.getVacancyName()+"\',"+worker.getSalary()+")";
        int i = stmt.executeUpdate(sqlCommand);
    }
    public Worker selectOne (int id) throws SQLException {
        Statement stmt = connectWay.createStatement();

        String sqlCommand = "SELECT * FROM weather where id = "+ id;
        ResultSet resSet = stmt.executeQuery(sqlCommand);
        while (resSet.next()){
            return new Worker (resSet.getInt("id"),resSet.getString("vacancyname"), resSet.getInt("salary"));
        }
        return null;
    }
    public void createTable() throws SQLException {
        Statement stmt = connectWay.createStatement();
        String sqlCommand = "create table test (id int, fieldInt int, fieldString char(10))";
        stmt.execute(sqlCommand);
    }
}
