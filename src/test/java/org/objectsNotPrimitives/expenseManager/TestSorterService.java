package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.service.SorterService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestSorterService {
    public static void main(String[] args) {
        testSorterService();
    }

    private static boolean testSorterService() {
        List<Transaction> testTransactionList = List.of(
                new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD),
                new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER),
                new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER),
                new Transaction(3, 350000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT),
                new Transaction(12, 250000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD),
                new Transaction(4, 350000L, Date.valueOf("2020-12-15"), "x6", TypesOfExpenses.OTHER),
                new Transaction(2, 250000L, Date.valueOf("2020-12-12"), "x7", TypesOfExpenses.OTHER),
                new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT));

        SorterService sorterService = new SorterService();
        List<Transaction> stringList = testTransactionList.stream()
                .sorted(sorterService.getComparator("type"))
                .collect(Collectors.toList());
        if (stringList.get(0).equals(testTransactionList.get(0)) && stringList.get(4).equals(testTransactionList.get(1))) {
            System.out.println("Sorter test passed");
            return true;
        } else {
            System.out.println("Sorter test not passed, objects not sorted");
            return false;
        }
    }
}