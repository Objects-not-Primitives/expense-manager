package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.service.SorterService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TestSorterService {
    public static void main(String[] args) {
        testSorterService();
    }

    private static boolean testSorterService() {
        Transaction testTransaction1 = new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD);
        Transaction testTransaction2 = new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER);
        Transaction testTransaction3 = new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER);
        Transaction testTransaction4 = new Transaction(3, 350000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT);
        Transaction testTransaction5 = new Transaction(12, 250000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD);
        Transaction testTransaction6 = new Transaction(4, 350000L, Date.valueOf("2020-12-15"), "x6", TypesOfExpenses.OTHER);
        Transaction testTransaction7 = new Transaction(2, 250000L, Date.valueOf("2020-12-12"), "x7", TypesOfExpenses.OTHER);
        Transaction testTransaction8 = new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT);

        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(testTransaction1);
        testTransactionList.add(testTransaction2);
        testTransactionList.add(testTransaction3);
        testTransactionList.add(testTransaction4);
        testTransactionList.add(testTransaction5);
        testTransactionList.add(testTransaction6);
        testTransactionList.add(testTransaction7);
        testTransactionList.add(testTransaction8);
        SorterService sorterService = new SorterService(testTransactionList);
        List<String> stringList = new ArrayList<>();
        sorterService.getSortMap().get("type").map(Transaction::getPurpose).forEach(stringList::add);
        if (stringList.get(1).equals("x5") && stringList.get(7).equals("x7")) {
            System.out.println("Сортировка работает");
            return true;
        } else {
            System.out.println("Тест не пройден, сортировка не работает");
            return false;
        }
    }
}