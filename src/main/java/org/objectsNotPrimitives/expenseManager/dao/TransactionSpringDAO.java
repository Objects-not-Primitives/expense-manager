package org.objectsNotPrimitives.expenseManager.dao;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.utils.SpringPropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class TransactionSpringDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionSpringDAO(SpringPropertyLoader properties) {
        SimpleDriverDataSource dataSource1 = new SimpleDriverDataSource();
        dataSource1.setPassword(properties.getPassword());
        dataSource1.setUsername(properties.getUsername());
        dataSource1.setUrl(properties.getUrl());
        dataSource1.setDriverClass(org.postgresql.Driver.class);
        this.jdbcTemplate = new JdbcTemplate(dataSource1);
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

