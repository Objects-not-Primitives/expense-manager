package org.objectsNotPrimitives.expenseManager.servlet;

import org.objectsNotPrimitives.expenseManager.exceptions.BadRequestException;
import org.objectsNotPrimitives.expenseManager.exceptions.NotFoundException;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String doGetOne(@RequestParam String id) {
        try {
            if (id != null) {
                return transactionService.getOne(id).toString();
            } else {
                throw new BadRequestException();
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        }
    }

    @GetMapping("/sort")
    public Stream<Transaction> doGetSort(@RequestParam String sortType) {
        if (sortType != null) {
            try {
                return transactionService.getSortedTransactions(sortType);
            } catch (NullPointerException e) {
                throw new NotFoundException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @GetMapping("/getType")
    public Stream<Transaction> doGetType(@RequestParam String getType) {
        if (getType != null) {
            try {
                return transactionService.getType(getType);
            } catch (IllegalArgumentException e) {
                throw new NotFoundException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @GetMapping("/getSum")
    public long doGetSum() {
        return transactionService.getSummaryOfValue();
    }

    @GetMapping("/getAll")
    public Stream<Transaction> doGetAll() {
        return transactionService.getAll();
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
        if (id != null) {
            try {
                transactionService.delete(Integer.parseInt(id));

            } catch (NumberFormatException e) {

                throw new BadRequestException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/type")
    public void doDeleteType(@RequestParam String type) {
        if (type != null) {
            transactionService.deleteType(type);

        } else {
            throw new BadRequestException();
        }

    }
}

