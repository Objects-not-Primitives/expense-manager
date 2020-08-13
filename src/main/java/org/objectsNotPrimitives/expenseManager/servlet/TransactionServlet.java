package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/transaction/*"})
public class TransactionServlet extends HttpServlet {
    private static final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        switch (req.getPathInfo()) {
            case ("/getByID/"): {
                if (req.getParameter("id") != null) {
                    servletWriter(transactionService.getOne(req.getParameter("id")), resp);
                } else {
                    servletWriter("There is no id parameter", resp);
                }
                break;
            }
            case ("/getBySortType/"): {
                if (req.getParameter("sortType") != null) {
                    servletWriter(transactionService.getSortedTransactions(req.getParameter("sortType")), resp);
                } else {
                    servletWriter("There is no sortType parameter", resp);
                }
                break;
            }
            case ("/getSum/"): {
                servletWriter(transactionService.getSummaryOfValue(), resp);
                break;
            }
            case ("/getAll/"): {
                servletWriter(transactionService.getAll(), resp);
                break;
            }
            default: {
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
            servletWriter(transactionService.post(jsonString), resp);
        } catch (IOException e) {
            servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            servletWriter(transactionService.put(jsonString), resp);
        } catch (IOException e) {
            servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        servletWriter(transactionService.delete(id), resp);
    }

    public void servletWriter(String text, HttpServletResponse resp) {
        try {
            resp.getWriter().println(text);
        } catch (IOException e) {
            System.out.println("Output problems");
            e.printStackTrace();
        }
    }
}

