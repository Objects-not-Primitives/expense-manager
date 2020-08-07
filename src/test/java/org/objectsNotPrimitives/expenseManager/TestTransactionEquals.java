package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class TestTransactionEquals {

    public static void main(String[] args) {
        TestTransactionEquals.employeeEqualsTest();
    }

    private static void employeeEqualsTest() {
        Set<Transaction> transactionSet = new HashSet<>();

        Transaction transaction1 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Transaction transaction2 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);

        transactionSet.add(transaction1);
        transactionSet.add(transaction2);

        int expectedSize = 1;

        if (transactionSet.size() == expectedSize) {
            System.out.println("transaction equals test passed");
        } else {
            System.out.println("transaction equals test failed, expected size of set was "
                    + expectedSize + " but actually is " + transactionSet.size());
        }
    }
}
