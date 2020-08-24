package org.objectsNotPrimitives.expenseManager.dao;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class TransactionSpringDAO {

    private static TransactionSpringDAO instance;
    private final JdbcTemplate jdbcTemplate;

    private TransactionSpringDAO(SimpleDriverDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static TransactionSpringDAO getInstance(Properties dbProperties) {
        if (instance == null) {
            SimpleDriverDataSource dataSource = connect(dbProperties);
            instance = new TransactionSpringDAO(dataSource);
        }
        return instance;
    }

    public static SimpleDriverDataSource connect(Properties dbProperties) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername(dbProperties.getProperty("db.login"));
        dataSource.setUrl(dbProperties.getProperty("db.url"));
        dataSource.setPassword(dbProperties.getProperty("db.password"));
        return dataSource;
    }

    public void updateRecord(Transaction transaction) {

        String sqlCommand = "update transaction set value = ?, date = ?, purpose = ?, types = ? where id = ?";
        jdbcTemplate.update(sqlCommand,
                transaction.getValue(),
                transaction.getDate(),
                transaction.getPurpose(),
                transaction.getType().getTypesOfExpenses(),
                transaction.getId());
    }

    public void deleteRecord(int transactionId) {
        String sqlCommand = "delete from transaction where id = ?";
        jdbcTemplate.update(sqlCommand, transactionId);
    }

    public void deleteTypeRecord(String type) {
        String sqlCommand = "delete from transaction where types = ?";
        jdbcTemplate.update(sqlCommand, type);
    }

    public void insertRecord(Transaction transaction) {
        String sqlCommand = "insert into transaction values (?,?,?,?,?)";
        jdbcTemplate.update(sqlCommand,
                transaction.getId(),
                transaction.getValue(),
                transaction.getDate(),
                transaction.getPurpose(),
                transaction.getType().getTypesOfExpenses());
    }

    public Optional<Transaction> selectOne(int id) {
        String sqlCommand = "SELECT * FROM transaction where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sqlCommand, new Object[]{id}, (rs, rowNum) -> new Transaction(rs.getInt("id"), rs.getLong("value"),
                rs.getDate("date"), rs.getString("purpose"),
                TypesOfExpenses.valueOf(rs.getString("types")))));
    }


    public Stream<Transaction> selectOneType(String type) {
        String sqlCommand = "SELECT * FROM transaction where types = ?";
        List<Transaction> employeesList = jdbcTemplate.query(sqlCommand, new Object[]{type},
                (rs, rowNum) -> new Transaction(rs.getInt("id"),
                        rs.getLong("value"),
                        rs.getDate("date"), rs.getString("purpose"),
                        TypesOfExpenses.valueOf(rs.getString("types"))));
        return employeesList.stream();
    }

    public Stream<Transaction> selectAll() {
        String sqlCommand = "SELECT * FROM transaction ";
        List<Transaction> employeesList = jdbcTemplate.query(sqlCommand,
                (rs, rowNum) -> new Transaction(rs.getInt("id"),
                        rs.getLong("value"),
                        rs.getDate("date"), rs.getString("purpose"),
                        TypesOfExpenses.valueOf(rs.getString("types"))));
        return employeesList.stream();
    }
}

