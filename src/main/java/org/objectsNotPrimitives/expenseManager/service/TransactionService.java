package org.objectsNotPrimitives.expenseManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.utils.PropertyLoader;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.dao.TransactionDAO;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String propertiesPath = "application.properties";
    private static String respString;

    private TransactionDAO transactionDAO;

    public TransactionService() {
        try {
            this.transactionDAO = TransactionDAO.getInstance(PropertyLoader.load(propertiesPath));
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("No connection to database");
        }
    }

    public String getOne(String id) {
        try {
             Optional<Transaction> optionalTransaction = transactionDAO.selectOne(Integer.parseInt(id));
                    if( optionalTransaction.isPresent()) {
                        respString = transactionToJson(optionalTransaction.get());
                    }else{
                        respString = "There is no transaction with such id";
                    }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
           respString = "No connection to database";
        }
        return respString;
    }

    public String getAll() {
        try {
            respString = transactionDAO.selectAll()
                    .map(this::transactionToJson)
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString = "No connection to database";
        }
        return respString;
    }

    public String getSummaryOfValue(){
        try {
            respString ="Summary of Transaction values: " + transactionDAO.selectAll()
                    .mapToLong(Transaction::getValue).sum();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString = "No connection to database";
        }
        return respString;
    }

    public String post(String jsonString) {
        try {
            transactionDAO.insertRecord(Objects.requireNonNull(jsonToTransaction(jsonString)));
            respString = "New transaction added";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString ="No connection to database";
        }
        return respString;
    }

    public String put(String jsonString) {
        try {
            transactionDAO.updateRecord(Objects.requireNonNull(jsonToTransaction(jsonString)));
            respString = "Transaction updated";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString ="No connection to database";
        }
        return respString;
    }

    public String delete(int id) {
        try {
            transactionDAO.deleteRecord(id);
            respString = "Transaction deleted";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString ="No connection to database";
        }
        return respString;
    }

    public String getSortedTransactions(String sortType) {
        try {
            SorterService sorterService = new SorterService ();
            respString = transactionDAO.selectAll()
                    .sorted(sorterService.getComparator(sortType))
                    .map(this::transactionToJson)
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            respString ="No connection to database";
        }
        return respString;
    }



    private String transactionToJson(Transaction transaction) {
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Problems encountered when processing (parsing, generating) JSON content";
        }
    }

    private Transaction jsonToTransaction(String jsonString) {
        try {
            return mapper.readValue(jsonString, Transaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            return null;
        }
    }
}
