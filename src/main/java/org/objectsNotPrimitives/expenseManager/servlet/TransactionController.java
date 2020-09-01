package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.exceptions.BadRequestException;
import org.objectsNotPrimitives.expenseManager.exceptions.NotFoundException;
import org.objectsNotPrimitives.expenseManager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService transactionService;
    ResponseConstructor responseConstructor;

    @Autowired
    public TransactionController(TransactionService transactionService, ResponseConstructor responseConstructor) {
        this.transactionService = transactionService;
        this.responseConstructor = responseConstructor;
    }

    @GetMapping("/")
    public String doGetOne(@RequestParam String id) {
        try {
            return responseConstructor.transactionToJson(transactionService.getOne(id));
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @GetMapping("/sort")
    public String doGetSort(@RequestParam String sortType) {
        try {
            return responseConstructor.transactionStreamToJson(transactionService.getSortedTransactions(sortType));
        } catch (NullPointerException e) {
            throw new NotFoundException();
        }
    }

    @GetMapping("/getType")
    public String doGetType(@RequestParam String getType) {
        try {
            return responseConstructor.transactionStreamToJson(transactionService.getType(getType));
        } catch (IllegalArgumentException e) {
            throw new NotFoundException();
        }
    }

    @GetMapping("/getSum")
    public String doGetSum() {
        return responseConstructor.getSummaryResponse(transactionService.getSummaryOfValue());
    }

    @GetMapping("/getAll")
    public String doGetAll() {
        return responseConstructor.transactionStreamToJson(transactionService.getAll());
    }

    @PostMapping
    public void doPost(@RequestBody String jsonTransaction) {
        transactionService.post(jsonTransaction);
    }

    @PutMapping
    public void doPut(@RequestBody String jsonTransaction) {
        transactionService.put(jsonTransaction);
    }

    @DeleteMapping
    public void doDeleteId(@RequestParam String id) {
        try {
            transactionService.delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/type")
    public void doDeleteType(@RequestParam String type) {
        transactionService.deleteType(type);
    }
}

