package org.objectsNotPrimitives.expenseManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.utils.PropertyLoader;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.dao.TransactionDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String propertiesPath = "application.properties";
    private static String respString;

    private TransactionDAO transactionDAO;

    public TransactionService() {
        try {
            this.transactionDAO = TransactionDAO.getInstance(PropertyLoader.load(propertiesPath));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction getOne(String id) throws SQLException, NumberFormatException {
        Optional<Transaction> optionalTransaction = transactionDAO.selectOne(Integer.parseInt(id));
        if (optionalTransaction.isPresent()) {
            return optionalTransaction.get();
        } else {
            return null;
        }
    }

    public Stream<Transaction> getType(String type) throws SQLException {
        return transactionDAO.selectOneType(type);
    }

    public Stream<Transaction> getAll() throws SQLException {
        return transactionDAO.selectAll();
    }

    public long getSummaryOfValue() throws SQLException {
        return transactionDAO.selectAll()
                .mapToLong(Transaction::getValue).sum();
    }

    public void post(String jsonString) throws SQLException {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        if (optionalTransaction.isPresent()) {
            transactionDAO.insertRecord(optionalTransaction.get());
        }
    }

    public void put(String jsonString) throws SQLException {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        if (optionalTransaction.isPresent()) {
            transactionDAO.updateRecord(optionalTransaction.get());

        }
    }

    //Метод не тестировался
    /*public String putType(String jsonString, String type) throws SQLException, JsonProcessingException {
        Transaction[] transactions = mapper.readValue(jsonString, Transaction[].class);
        transactionDAO.updateTypeRecord(Arrays.stream(transactions), type);
        respString = "Transactions updated";
        return respString;
    }*/

    public void delete(int id) throws SQLException {
        transactionDAO.deleteRecord(id);
    }

    public void deleteType(String type) throws SQLException {
        transactionDAO.deleteTypeRecord(type);
    }

    public Stream<Transaction> getSortedTransactions(String sortType) throws SQLException {
        SorterService sorterService = new SorterService();
        return transactionDAO.selectAll()
                .sorted(sorterService.getComparator(sortType));
    }

    private Optional<Transaction> jsonToTransaction(String jsonString) {
        try {
            return Optional.of(mapper.readValue(jsonString, Transaction.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            return Optional.empty();
        }
    }
}
