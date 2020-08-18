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

public class TransactionService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String propertiesPath = "application.properties";
    private static String respString;

    private final TransactionDAO transactionDAO;

    public TransactionService() throws SQLException {
        this.transactionDAO = TransactionDAO.getInstance(PropertyLoader.load(propertiesPath));
    }

    public String getOne(String id) throws SQLException, NumberFormatException {
        Optional<Transaction> optionalTransaction = transactionDAO.selectOne(Integer.parseInt(id));
        if (optionalTransaction.isPresent()) {
            respString = transactionToJson(optionalTransaction.get());
        } else {
            respString = "";
        }
        return respString;
    }

    public String getType(String type) throws SQLException {
        respString = transactionDAO.selectOneType(type)
                .map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
        return respString;
    }

    public String getAll() throws SQLException {
        respString = transactionDAO.selectAll()
                .map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
        return respString;
    }

    public String getSummaryOfValue() throws SQLException {
        respString = "Summary of Transaction values: " + transactionDAO.selectAll()
                .mapToLong(Transaction::getValue).sum();
        return respString;
    }

    public String post(String jsonString) throws SQLException {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        if (optionalTransaction.isPresent()) {
            transactionDAO.insertRecord(optionalTransaction.get());
            respString = "New transaction added";
        } else {
            respString = "";
        }
        return respString;
    }

    public String put(String jsonString) throws SQLException {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        if (optionalTransaction.isPresent()) {
            transactionDAO.updateRecord(optionalTransaction.get());
            respString = "Transaction updated";
        } else {
            respString = "Didn't get valid Transaction";
        }
        return respString;
    }

    //Метод не тестировался
    public String putType(String jsonString, String type) throws SQLException, JsonProcessingException {
        Transaction[] transactions = mapper.readValue(jsonString, Transaction[].class);
        transactionDAO.updateTypeRecord(Arrays.stream(transactions), type);
        respString = "Transactions updated";
        return respString;
    }

    public String delete(int id) throws SQLException {
        transactionDAO.deleteRecord(id);
        respString = "Transaction deleted";
        return respString;
    }

    public String deleteType(String type) throws SQLException {
        transactionDAO.deleteTypeRecord(type);
        return respString;
    }

    public String getSortedTransactions(String sortType) throws SQLException {
        SorterService sorterService = new SorterService();
        respString = transactionDAO.selectAll()
                .sorted(sorterService.getComparator(sortType))
                .map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
        return respString;
    }

    private String transactionToJson(Transaction transaction) {
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
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
