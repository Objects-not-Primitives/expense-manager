package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/first-servlet/*"})
public class TransactionServlet extends HttpServlet {
    private static final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String id = (req.getParameter("id"));
        if (id == null) {
            transactionService.getAll(resp);
        } else {
            transactionService.getOne(id, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.post(jsonString, resp);
        } catch (IOException e) {
            transactionService.servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = req.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            transactionService.put(jsonString, resp);
        } catch (IOException e) {
            transactionService.servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        transactionService.delete(id, resp);
    }
}

