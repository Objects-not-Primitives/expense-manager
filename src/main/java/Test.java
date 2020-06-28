import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws SQLException, IOException {
        Properties property = new Properties();
        property.load(new FileInputStream("src/main/resources/application.properties"));

        Worker cleaner = new Worker(1, "cleaner", 350);
        WorkerDAO workerDAO = new WorkerDAO(DriverManager.getConnection(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password")));
        boolean fuckThisShit = workerDAO.connect(
                property.getProperty("db.url"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));


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