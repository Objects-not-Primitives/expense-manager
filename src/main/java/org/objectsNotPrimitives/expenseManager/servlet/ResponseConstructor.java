package org.objectsNotPrimitives.expenseManager.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.model.Transaction;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResponseConstructor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public ResponseConstructor() {
    }

    public String getOne(Transaction transaction) {
        return transactionToJson(transaction);
    }

    public String getResult(Stream<Transaction> transactionStream) {
        return transactionStream
                .map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getSummaryOfValue(long sum) {
        return "Summary of Transaction values: " + sum;
    }

    public String getSortedTransactions(Stream<Transaction> transactionStream) {
        return transactionStream.map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
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
