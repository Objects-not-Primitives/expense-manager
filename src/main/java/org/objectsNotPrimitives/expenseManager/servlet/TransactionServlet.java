package org.objectsNotPrimitives.expenseManager.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet(urlPatterns = {"/transaction/*"})
public class TransactionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ExceptionDispatcher exceptionDispatcher = new ExceptionDispatcher(resp);
        switch (req.getPathInfo()) {
            case ("/"): {
                exceptionDispatcher.getOne(req.getParameter("id"), resp);
                break;
            }
            case ("/sort/"): {
                exceptionDispatcher.getSortedTransactions(req.getParameter("sortType"), resp);
                break;
            }
            case ("/getType/"): {
                exceptionDispatcher.getType(req.getParameter("getType"), resp);
                break;
            }
            case ("/getSum/"): {
                exceptionDispatcher.getSummaryOfValue(resp);
                break;
            }
            case ("/getAll/"): {
                exceptionDispatcher.getAll(resp);
                break;
            }
            default: {
                resp.setStatus(SC_NOT_FOUND);
                exceptionDispatcher.servletWriter("Non-existing path", resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ExceptionDispatcher exceptionDispatcher = new ExceptionDispatcher(resp);
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            exceptionDispatcher.post(jsonString, resp);
        } catch (IOException e) {
            exceptionDispatcher.servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ExceptionDispatcher exceptionDispatcher = new ExceptionDispatcher(resp);
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            exceptionDispatcher.put(jsonString, resp);
        } catch (IOException e) {
            exceptionDispatcher.servletWriter("Invalid HTTP request", resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        ExceptionDispatcher exceptionDispatcher = new ExceptionDispatcher(resp);
        if (req.getParameterMap().containsKey("id")) {
            exceptionDispatcher.delete(req.getParameter("id"), resp);
        } else if (req.getParameterMap().containsKey("type")) {
            exceptionDispatcher.deleteType(req.getParameter("type"), resp);
        } else {
            resp.setStatus(SC_NOT_FOUND);
            exceptionDispatcher.servletWriter("Invalid HTTP request", resp);
        }
    }
}

