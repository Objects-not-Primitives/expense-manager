package org.objectsNotPrimitives.expenseManager.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.service.SorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;
@Component
public class TransactionService {
    private static final ObjectMapper mapper = new ObjectMapper();


    private TransactionSpringDAO transactionDAO;
    @Autowired
    public TransactionService(TransactionSpringDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public Transaction getOne(String id) throws NumberFormatException {
        Optional<Transaction> optionalTransaction = transactionDAO.selectOne(Integer.parseInt(id));
        return optionalTransaction.orElse(null);
    }

    public Stream<Transaction> getType(String type) {
        return transactionDAO.selectOneType(type);
    }

    public Stream<Transaction> getAll() {
        return transactionDAO.selectAll();
    }

    public long getSummaryOfValue() {
        return transactionDAO.selectAll()
                .mapToLong(Transaction::getValue).sum();
    }

    public void post(String jsonString) {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        optionalTransaction.ifPresent(transactionDAO::insertRecord);
    }

    public void put(String jsonString) {
        Optional<Transaction> optionalTransaction = jsonToTransaction(jsonString);
        optionalTransaction.ifPresent(transactionDAO::updateRecord);
    }

    public void delete(int id) {
        transactionDAO.deleteRecord(id);
    }

    public void deleteType(String type) {
        transactionDAO.deleteTypeRecord(type);
    }

    public Stream<Transaction> getSortedTransactions(String sortType) {
        SorterService sorterService = new SorterService();
        return transactionDAO.selectAll()
                .sorted(sorterService.getComparator(sortType));
    }

    private Optional<Transaction> jsonToTransaction(String jsonString) {
        try {
            return Optional.of(mapper.readValue(jsonString, Transaction.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Problems encountered when processing (parsing, generating) JSON content");
            return Optional.empty();
        }
    }
}
