package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/sorter-servlet/*"})
public class TransactionSorterServlet extends HttpServlet {
    private static final TransactionService transactionService = new TransactionService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String sortType = (req.getParameter("sortType"));
        transactionService.getSortedTransactions(sortType, resp);
    }
}