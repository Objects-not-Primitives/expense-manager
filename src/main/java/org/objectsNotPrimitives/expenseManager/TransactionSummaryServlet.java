package org.objectsNotPrimitives.expenseManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/summary-servlet/*"})
public class TransactionSummaryServlet extends HttpServlet {
    private static final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        transactionService.getSummaryOfValue(resp);
    }
}