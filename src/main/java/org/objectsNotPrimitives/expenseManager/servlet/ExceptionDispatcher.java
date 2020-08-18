package org.objectsNotPrimitives.expenseManager.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.service.TransactionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ExceptionDispatcher {

    private TransactionService transactionService;

    private static String respString;

    public ExceptionDispatcher(HttpServletResponse resp) {
        try {
            this.transactionService = new TransactionService();
        } catch (SQLException exception) {
            exception.printStackTrace();
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            servletWriter("No connection to database", resp);
        }
    }

    public void getOne(String id, HttpServletResponse resp) {
        if (id != null) {
            try {
                respString = transactionService.getOne(id);
                if (!respString.equals("")) {
                    servletWriter(respString, resp);
                } else {
                    resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                    servletWriter("There is no transaction with such id", resp);
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                servletWriter("No connection to database", resp);
                resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException throwable) {
                throwable.printStackTrace();
                servletWriter("Id is not an integer", resp);
                resp.setStatus(SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(SC_NOT_FOUND);
            servletWriter("There is no id parameter", resp);
        }
    }

    public void getType(String type, HttpServletResponse resp) {
        if (type != null) {
            try {
                TypesOfExpenses.valueOf(type);
                servletWriter(transactionService.getType(type), resp);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                servletWriter("No connection to database", resp);
                resp.setStatus(SC_NOT_FOUND);
            }  catch (IllegalArgumentException throwable) {
                throwable.printStackTrace();
                servletWriter("Non-existing type param", resp);
                resp.setStatus(SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(SC_NOT_FOUND);
            servletWriter("There is no getType parameter", resp);
        }

    }

    public void getAll(HttpServletResponse resp) {
        try {
            servletWriter(transactionService.getAll(), resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    public void getSummaryOfValue(HttpServletResponse resp) {
        try {
            servletWriter(transactionService.getSummaryOfValue(), resp);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    public void post(String jsonString, HttpServletResponse resp) {
        try {
            respString = transactionService.post(jsonString);
            if (!respString.equals("")) {
                respString = "New transaction added";
            } else {
                respString = "Didn't get valid Transaction";
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    public void put(String jsonString, HttpServletResponse resp) {
        try {
            respString = transactionService.put(jsonString);
            if (!respString.equals("")) {
                servletWriter(respString,resp);
            } else {
                servletWriter("Didn't get valid Transaction",resp);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    //Метод не тестировался
    public void putType(String jsonString, String type, HttpServletResponse resp) {
        try {
            transactionService.putType(jsonString, type);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            servletWriter("No connection to database", resp);
            resp.setStatus(SC_NOT_FOUND);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            servletWriter("Didn't get valid Transactions", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }


    public void delete(String id, HttpServletResponse resp) {
        if (id != null) {
            try {
                servletWriter(transactionService.delete(Integer.parseInt(id)), resp);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                servletWriter("No connection to database", resp);
                resp.setStatus(SC_NOT_FOUND);
            } catch (NumberFormatException throwable) {
                throwable.printStackTrace();
                servletWriter("Id is not an integer", resp);
                resp.setStatus(SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(SC_NOT_FOUND);
            servletWriter("There is no id parameter", resp);
        }
    }

    public void deleteType(String type, HttpServletResponse resp) {
        if (type != null) {
            try {
                servletWriter(transactionService.deleteType(type), resp);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                servletWriter("No connection to database", resp);
                resp.setStatus(SC_NOT_FOUND);
            }
        } else {
            servletWriter("There is no type parameter", resp);
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    public void getSortedTransactions(String sortType, HttpServletResponse resp) {
        if (sortType != null) {
            try {
                servletWriter(transactionService.getSortedTransactions(sortType), resp);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                servletWriter("No connection to database", resp);
                resp.setStatus(SC_NOT_FOUND);
            } catch (NullPointerException throwable) {
                throwable.printStackTrace();
                servletWriter("There is no such sortType", resp);
                resp.setStatus(SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(SC_NOT_FOUND);
            servletWriter("There is no sortType parameter", resp);
        }
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
}
