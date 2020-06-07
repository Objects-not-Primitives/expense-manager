import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws SQLException {
        Worker cleaner = new Worker(1,"cleaner", 350);
        WorkerDAO workerDAO = new WorkerDAO(DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Fynjy_0613"));
        boolean fuckThisShit = workerDAO.connect(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Fynjy_0613");


        workerDAO.createTable();

        //Test.connectToAndQueryDatabase("postgres", "Fynjy_0613");
    }
    /*public static void connectToAndQueryDatabase(String username, String password) throws SQLException {

        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                username,
                password);
        Statement stmt = con.createStatement();
        Worker autist = new Worker(50,"autist");
        String l1 = autist.getVacancyName();
        int l2 = autist.getSalary();
        String sqlCommand = "insert into vacancy values("+"\' "+l1+"\' "+","+l2+")";
        int rs2 = stmt.executeUpdate(sqlCommand);

    }*/
}