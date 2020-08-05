package org.objectsNotPrimitives.expenseManager;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.compareTo(o2);
    }
}
