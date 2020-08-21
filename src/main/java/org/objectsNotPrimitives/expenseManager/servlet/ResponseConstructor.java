package org.objectsNotPrimitives.expenseManager.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.model.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.servlet.http.HttpServletResponse.*;

public class ResponseConstructor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public ResponseConstructor() {
    }

    public String getOne(Transaction transaction) {
        return transactionToJson(transaction);
    }

    public String getType(Stream<Transaction> transactionStream) {
        return transactionStream.map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getAll(Stream<Transaction> transactionStream) {
        return transactionStream
                .map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getSummaryOfValue(long sum) {
        return "Summary of Transaction values: " + sum;
    }

    public String post() {
        return "New transaction added";
    }

    public String put() {
        return "Transaction updated";
    }

    //Метод не тестировался
    /*public void putType(String jsonString, String type, HttpServletResponse resp) {
        try {
            transactionService.putType(jsonString, type);
        } catch (SQLException throwable) {
            getSQLException(resp, throwable);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            servletWriter("Didn't get valid Transactions", resp);
            resp.setStatus(SC_BAD_REQUEST);
        }
    }*/

    public String delete() {
        return "Transaction deleted";
    }

    public String deleteType() {
        return "Transactions deleted";
    }

    public String getSortedTransactions(Stream<Transaction> transactionStream) {
        return transactionStream.map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void servletWriter(String text, HttpServletResponse resp) {
        try {
            resp.getWriter().println(text);
        } catch (IOException e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            System.out.println("Output problems");
            e.printStackTrace();
        }
    }

    private String transactionToJson(Transaction transaction) {
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
