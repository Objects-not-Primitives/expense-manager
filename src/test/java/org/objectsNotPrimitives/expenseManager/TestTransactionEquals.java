package org.objectsNotPrimitives.expenseManager;

import java.util.HashSet;
import java.util.Set;

public class TestTransactionEquals {

    public static void main(String[] args) {
        TestTransactionEquals.employeeEqualsTest();
    }

    private static void employeeEqualsTest() {
        Set<Transaction> transactionSet = new HashSet<>();

        Transaction transaction1 = new Transaction(1, "lol", 100);
        Transaction transaction2 = new Transaction(1, "lol", 100);

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
