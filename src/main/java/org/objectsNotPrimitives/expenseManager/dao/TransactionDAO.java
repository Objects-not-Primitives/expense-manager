package org.objectsNotPrimitives.expenseManager.dao;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class TransactionDAO implements AutoCloseable {

    private static TransactionDAO instance;
    private final Connection connectWay;

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
                dbProperties.getProperty("spring.datasource.url"),
                dbProperties.getProperty("spring.datasource.username"),
                dbProperties.getProperty("spring.datasource.password"));
    }

    public void updateRecord(Transaction transaction) throws SQLException {
        String sqlCommand = "update transaction set value = ?, date = ?, purpose = ?, types = ? where id = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, transaction.getValue());
            preparedStatement.setDate(2, transaction.getDate());
            preparedStatement.setString(3, transaction.getPurpose());
            preparedStatement.setString(4, transaction.getType().getTypesOfExpenses());
            preparedStatement.setInt(5, transaction.getId());
            int i = preparedStatement.executeUpdate();
        }
    }

    public void updateTypeRecord(Stream<Transaction> transactionStream, String type) throws SQLException {
        String sqlCommand = "update transaction set types = ? where id = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, type);
            transactionStream.map(Transaction::getId).forEach(t -> {
                        try {
                            preparedStatement.setInt(2, t);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
            );
            int i = preparedStatement.executeUpdate();
        }
    }

    public void deleteRecord(int transactionId) throws SQLException {
        String sqlCommand = "delete from transaction where id = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, transactionId);
            int i = preparedStatement.executeUpdate();
        }
    }

    public void deleteTypeRecord(String type) throws SQLException {
        String sqlCommand = "delete from transaction where types = ?";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, type);
            int i = preparedStatement.executeUpdate();
        }
    }

    public void insertRecord(Transaction transaction) throws SQLException {
        String sqlCommand = "insert into transaction values (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connectWay.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.setLong(2, transaction.getValue());
            preparedStatement.setDate(3, transaction.getDate());
            preparedStatement.setString(4, transaction.getPurpose());
            preparedStatement.setString(5, transaction.getType().getTypesOfExpenses());
            int i = preparedStatement.executeUpdate();
        }
    }

    public Optional<Transaction> selectOne(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connectWay.prepareStatement("SELECT * FROM transaction where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        new Transaction(resultSet.getInt("id"), resultSet.getLong("value"),
                                resultSet.getDate("date"), resultSet.getString("purpose"),
                                TypesOfExpenses.valueOf(resultSet.getString("types")))
                );
            }
            return Optional.empty();
        }
    }

    public Stream<Transaction> selectOneType(String type) throws SQLException {
        try (PreparedStatement preparedStatement = connectWay.prepareStatement("SELECT * FROM transaction where types = ?")) {
            List<Transaction> employeesList = new ArrayList<>();
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeesList.add(new Transaction(resultSet.getInt("id"), resultSet.getLong("value"),
                        resultSet.getDate("date"), resultSet.getString("purpose"),
                        TypesOfExpenses.valueOf(resultSet.getString("types"))));
            }
            return employeesList.stream();
        }
    }

    public Stream<Transaction> selectAll() throws SQLException {
        try (Statement stmt = connectWay.createStatement()) {
            List<Transaction> employeesList = new ArrayList<>();
            String sqlCommand = "SELECT * FROM transaction ";
            ResultSet resultSet = stmt.executeQuery(sqlCommand);
            while (resultSet.next()) {
                employeesList.add(new Transaction(resultSet.getInt("id"), resultSet.getLong("value"),
                        resultSet.getDate("date"), resultSet.getString("purpose"),
                        TypesOfExpenses.valueOf(resultSet.getString("types"))));
            }
            return employeesList.stream();
        }
    }

    @Override
    public void close() throws SQLException {
        connectWay.close();
    }
}
