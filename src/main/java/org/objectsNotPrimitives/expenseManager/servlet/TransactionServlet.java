package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(urlPatterns = {"/transaction/*"})
public class TransactionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor();
        TransactionService transactionService = new TransactionService();
        switch (req.getPathInfo()) {
            case ("/"): {
                try {
                    String id = req.getParameter("id");
                    if (id != null) {
                        Transaction transaction = transactionService.getOne(id);
                        responseConstructor.getOne(transaction);
                    } else {
                        resp.setStatus(SC_BAD_REQUEST);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    resp.setStatus(SC_BAD_REQUEST);
                }
                break;
            }
            case ("/sort/"): {
                if (req.getParameter("sortType") != null) {
                    try {
                        Stream<Transaction> transactionStream = transactionService.getSortedTransactions(req.getParameter("sortType"));
                        responseConstructor.getSortedTransactions(transactionStream);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                    } catch (NullPointerException throwable) {
                        throwable.printStackTrace();
                        resp.setStatus(SC_NOT_FOUND);
                    }
                } else {
                    resp.setStatus(SC_BAD_REQUEST);
                }

                break;
            }
            case ("/getType/"): {
                if (req.getParameter("getType") != null) {
                    try {
                        Stream<Transaction> transactionStream = transactionService.getType(req.getParameter("getType"));
                        responseConstructor.getType(transactionStream);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                    } catch (IllegalArgumentException throwable) {
                        throwable.printStackTrace();
                        resp.setStatus(SC_NOT_FOUND);
                    }
                    break;
                } else {
                    resp.setStatus(SC_BAD_REQUEST);
                }
            }
            case ("/getSum/"): {
                try {
                    long sum = transactionService.getSummaryOfValue();
                    responseConstructor.getSummaryOfValue(sum);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                }

                break;
            }
            case ("/getAll/"): {
                try {
                    Stream<Transaction> transactionStream = transactionService.getAll();
                    responseConstructor.getAll(transactionStream);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                }
                break;
            }
            default: {
                resp.setStatus(SC_NOT_FOUND);
                responseConstructor.servletWriter("Non-existing path", resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor();
        TransactionService transactionService = new TransactionService();
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.post(jsonString);
            responseConstructor.post();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor();
        TransactionService transactionService = new TransactionService();
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.put(jsonString);
            responseConstructor.put();
        } catch (IOException | SQLException e) {
            catchBadRequest(resp, responseConstructor);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor();
        TransactionService transactionService = new TransactionService();
        if (req.getParameterMap().containsKey("id")) {
            if (req.getParameter("id") != null) {
                try {
                    transactionService.delete(Integer.parseInt(req.getParameter("id")));
                    responseConstructor.delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.setStatus(SC_INTERNAL_SERVER_ERROR);
                } catch (NumberFormatException throwable) {
                    throwable.printStackTrace();
                    resp.setStatus(SC_BAD_REQUEST);
                }
            } else {
                resp.setStatus(SC_BAD_REQUEST);
            }
        } else if (req.getParameterMap().containsKey("type")) {
            if (req.getParameter("type") != null) {
                try {
                    transactionService.deleteType(req.getParameter("type"));
                    responseConstructor.deleteType();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                resp.setStatus(SC_BAD_REQUEST);
            }
        } else {
            catchBadRequest(resp, responseConstructor);
        }
    }

    private void catchBadRequest(HttpServletResponse resp, ResponseConstructor responseConstructor) {
        responseConstructor.servletWriter("Invalid HTTP request", resp);
        resp.setStatus(SC_BAD_REQUEST);
    }
}

