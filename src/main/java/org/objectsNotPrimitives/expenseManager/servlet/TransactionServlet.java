package org.objectsNotPrimitives.expenseManager.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet(urlPatterns = {"/transaction/*"})
public class TransactionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor(resp);
        switch (req.getPathInfo()) {
            case ("/"): {
                responseConstructor.getOne(req.getParameter("id"), resp);
                break;
            }
            case ("/sort/"): {
                responseConstructor.getSortedTransactions(req.getParameter("sortType"), resp);
                break;
            }
            case ("/getType/"): {
                responseConstructor.getType(req.getParameter("getType"), resp);
                break;
            }
            case ("/getSum/"): {
                responseConstructor.getSummaryOfValue(resp);
                break;
            }
            case ("/getAll/"): {
                responseConstructor.getAll(resp);
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
        ResponseConstructor exceptionDispatcher = new ResponseConstructor(resp);
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            exceptionDispatcher.post(jsonString, resp);
        } catch (IOException e) {
            catchBadRequest(resp, exceptionDispatcher);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor(resp);
        try {
            String jsonString = req.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            responseConstructor.put(jsonString, resp);
        } catch (IOException e) {
            catchBadRequest(resp, responseConstructor);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        ResponseConstructor responseConstructor = new ResponseConstructor(resp);
        if (req.getParameterMap().containsKey("id")) {
            responseConstructor.delete(req.getParameter("id"), resp);
        } else if (req.getParameterMap().containsKey("type")) {
            responseConstructor.deleteType(req.getParameter("type"), resp);
        } else {
            catchBadRequest(resp, responseConstructor);
        }
    }

    private void catchBadRequest(HttpServletResponse resp, ResponseConstructor responseConstructor) {
        responseConstructor.servletWriter("Invalid HTTP request", resp);
        resp.setStatus(SC_BAD_REQUEST);
    }
}

