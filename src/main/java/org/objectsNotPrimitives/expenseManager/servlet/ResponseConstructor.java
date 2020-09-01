package org.objectsNotPrimitives.expenseManager.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ResponseConstructor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public ResponseConstructor() {
    }

    public String transactionToJson(Transaction transaction) {
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String transactionStreamToJson(Stream<Transaction> transactionStream) {
        return transactionStream.map(this::transactionToJson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getSummaryResponse(long sum) {
        return "Summary of Transaction values: " + sum;
    }

}
