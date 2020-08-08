package org.objectsNotPrimitives.expenseManager.service;

import org.objectsNotPrimitives.expenseManager.model.Transaction;

import java.util.*;

public class SorterService {

    private final Map<String, Comparator<Transaction>> fieldToComparator;

    public SorterService() {
        HashMap<String, Comparator<Transaction>> fieldToComparator = new HashMap<>();
        fieldToComparator.put("value", Comparator.comparing(Transaction::getValue));
        fieldToComparator.put("date", Comparator.comparing(Transaction::getDate));
        fieldToComparator.put("type", Comparator.comparing(Transaction::getType));
        this.fieldToComparator = fieldToComparator;
    }

    public Comparator<Transaction> getComparator(String keySort) {
        return fieldToComparator.get(keySort);
    }
}
