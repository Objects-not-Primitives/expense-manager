package org.objectsNotPrimitives.expenseManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String propertiesPath = "application.properties";

    private TransactionDAO transactionDAO;

    public TransactionService() {
        try {
            this.transactionDAO = TransactionDAO.getInstance(PropertyLoader.load(propertiesPath));
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("No connection to database");
        }
    }

    public void getOne(String id, HttpServletResponse resp) {
        try {
            transactionDAO.selectOne(Integer.parseInt(id)).ifPresentOrElse
                    (employeeFunc -> servletWriter(employeeToJson(employeeFunc), resp),
                            () -> servletWriter("There is no transaction with such id", resp));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void getAll(HttpServletResponse resp) {
        try {
            servletWriter(transactionDAO.selectAll().stream()
                    .map(this::employeeToJson)
                    .collect(Collectors.joining(System.lineSeparator())), resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void post(String jsonString, HttpServletResponse resp) {
        try {
            transactionDAO.insertRecord(Objects.requireNonNull(jsonToEmployee(jsonString)));
            servletWriter("New transaction added", resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void put(String jsonString, HttpServletResponse resp) {
        try {
            transactionDAO.updateRecord(Objects.requireNonNull(jsonToEmployee(jsonString)));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void delete(int id, HttpServletResponse resp) {
        try {
            transactionDAO.deleteRecord(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
        }
    }

    public void servletWriter(String text, HttpServletResponse resp) {
        try {
            resp.getWriter().println(text);
        } catch (IOException e) {
            System.out.println("Output problems");
            e.printStackTrace();
        }
    }

    private String employeeToJson(Transaction transaction) {
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Problems encountered when processing (parsing, generating) JSON content";
        }
    }

    private Transaction jsonToEmployee(String jsonString) {
        try {
            return mapper.readValue(jsonString, Transaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            return null;
        }
    }
}
