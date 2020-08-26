package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @GetMapping("/")
    public Transaction doGetOne(@RequestParam String id){
        System.out.println(id);
                //try {
                    //if (id != null) {
                       //return transactionService.getOne(id);}}
        return  new Transaction(11, 2500L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.FOOD);}}
                   /* } else {
                        catchBadRequest(resp);
                    }
                } catch (NumberFormatException e) {
                    catchBadRequest(resp);
                }
            }


            case ("/sort/"): {
                if (req.getParameter("sortType") != null) {
                    try {
                        Stream<Transaction> transactionStream = transactionService.getSortedTransactions(req.getParameter("sortType"));
                        servletWriter(responseConstructor.getSortedTransactions(transactionStream), resp);
                    } catch (SQLException e) {
                        catchInternalServerError(resp, e);
                    } catch (NullPointerException throwable) {
                        throwable.printStackTrace();
                        resp.setStatus(SC_NOT_FOUND);
                    }
                } else {
                    catchBadRequest(resp);
                }
                break;
            }
            case ("/getType/"): {
                if (req.getParameter("getType") != null) {
                    try {
                        Stream<Transaction> transactionStream = transactionService.getType(req.getParameter("getType"));
                        servletWriter(responseConstructor.getResult(transactionStream), resp);
                    } catch (SQLException e) {
                        catchInternalServerError(resp, e);
                    } catch (IllegalArgumentException throwable) {
                        throwable.printStackTrace();
                        resp.setStatus(SC_NOT_FOUND);
                    }
                } else {
                    catchBadRequest(resp);
                }
                break;
            }
            case ("/getSum/"): {
                try {
                    long sum = transactionService.getSummaryOfValue();
                    servletWriter(responseConstructor.getSummaryOfValue(sum), resp);
                } catch (SQLException e) {
                    catchInternalServerError(resp, e);
                }
                break;
            }
            case ("/getAll/"): {
                try {
                    Stream<Transaction> transactionStream = transactionService.getAll();
                    servletWriter(responseConstructor.getResult(transactionStream), resp);
                } catch (SQLException e) {
                    catchInternalServerError(resp, e);
                }
                break;
            }
            default: {
                resp.setStatus(SC_NOT_FOUND);
                servletWriter("Non-existing path", resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.post(jsonString);
            servletWriter("New transaction added", resp);
        } catch (SQLException e) {
            catchInternalServerError(resp, e);
        } catch (IOException e) {
            catchBadRequest(resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.put(jsonString);
            servletWriter("Transaction updated", resp);
        } catch (SQLException e) {
            catchInternalServerError(resp, e);
        } catch (IOException e) {
            catchBadRequest(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameterMap().containsKey("id")) {
            if (req.getParameter("id") != null) {
                try {
                    transactionService.delete(Integer.parseInt(req.getParameter("id")));
                    servletWriter("Transaction deleted", resp);
                } catch (SQLException e) {
                    catchInternalServerError(resp, e);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    catchBadRequest(resp);
                }
            } else {
                catchBadRequest(resp);
            }
        } else if (req.getParameterMap().containsKey("type")) {
            if (req.getParameter("type") != null) {
                try {
                    transactionService.deleteType(req.getParameter("type"));
                    servletWriter("Transactions deleted", resp);
                } catch (SQLException e) {
                    catchInternalServerError(resp, e);
                }
            } else {
                catchBadRequest(resp);
            }
        } else {
            catchBadRequest(resp);
        }
    }

    private void catchInternalServerError(HttpServletResponse resp, SQLException e) {
        e.printStackTrace();
        resp.setStatus(SC_INTERNAL_SERVER_ERROR);
    }

    private void catchBadRequest(HttpServletResponse resp) {
        servletWriter("Invalid HTTP request", resp);
        resp.setStatus(SC_BAD_REQUEST);
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
}*/