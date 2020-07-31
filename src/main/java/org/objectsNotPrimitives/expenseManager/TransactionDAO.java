package org.objectsNotPrimitives.expenseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class TransactionDAO implements AutoCloseable {

    private static TransactionDAO instance;
    private Connection connectWay;

    private TransactionDAO(Connection connectWay) {
        this.connectWay = connectWay;
    }

    public static TransactionDAO getInstance(Properties dbProperties) throws SQLException {
        if (instance == null) {
            Connection connection = connect(dbProperties);
            instance = new TransactionDAO(connection);
        }
        return instance;
    }

    public static Connection connect(Properties dbProperties) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(
                dbProperties.getProperty("db.url"),
                dbProperties.getProperty("db.login"),
                dbProperties.getProperty("db.password"));
    }

    public void updateRecord(Transaction transaction) throws SQLException {
        String sqlCommand = "update transaction set vacancy_name = ?, salary = ? where id = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, transaction.getVacancyName());
            preparedStatement.setInt(2, transaction.getSalary());
            preparedStatement.setInt(3, transaction.getId());
            int i = preparedStatement.executeUpdate();
        }

    }

    public void deleteRecord(int employeeId) throws SQLException {
        String sqlCommand = "delete from transaction where id = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, employeeId);
            int i = preparedStatement.executeUpdate();
        }
    }

    public void insertRecord(Transaction transaction) throws SQLException {
        String sqlCommand = "insert into transaction values (?,?,?)";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.setString(2, transaction.getVacancyName());
            preparedStatement.setInt(3, transaction.getSalary());
            int i = preparedStatement.executeUpdate();
        }
    }

    public Optional<Transaction> selectOne(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connectWay.prepareStatement("SELECT * FROM transaction where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Optional<Transaction> opt = Optional.of(new Transaction(resultSet.getInt("id"), resultSet.getString("vacancy_name"), resultSet.getInt("salary")));
                return opt;
            }
            return Optional.empty();
        }
    }

    public List<Transaction> selectAll() throws SQLException {
        try (Statement stmt = connectWay.createStatement()) {
            List<Transaction> employeesList = new ArrayList<>();
            String sqlCommand = "SELECT * FROM transaction ";
            ResultSet resSet = stmt.executeQuery(sqlCommand);
            while (resSet.next()) {
                employeesList.add(new Transaction(resSet.getInt("id"), resSet.getString("vacancy_name"), resSet.getInt("salary")));
            }
            return employeesList;
        }
    }

    @Override
    public void close() throws SQLException {
        connectWay.close();
    }
}
