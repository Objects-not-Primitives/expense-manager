package org.objectsNotPrimitives.expenseManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Временный тест для проверки работы компаратора для Transaction.
 * В реальности можно подумать над тем, чтобы убрать id из сортировки
 * т.к. такая информация для "пользователя не важна => нужно будет
 * пересмотреть метод compareTo() класса Transaction. В данный момент
 * в сортировке учавствуют все поля, кроме purpose. Приоритет сравнения
 * в таком порядке: id, value, date, type.
 */
public class TestTransactionComparator {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        testComparator();
    }

    private static boolean testComparator() throws NoSuchFieldException, IllegalAccessException {
        Transaction testTransaction1 = new Transaction(8, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD);
        Transaction testTransaction2 = new Transaction(8, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER);
        Transaction testTransaction3 = new Transaction(8, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER);
        Transaction testTransaction4 = new Transaction(8, 350000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT);
        Transaction testTransaction5 = new Transaction(2, 250000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD);
        Transaction testTransaction6 = new Transaction(2, 350000L, Date.valueOf("2020-12-15"), "x6", TypesOfExpenses.OTHER);
        Transaction testTransaction7 = new Transaction(2, 250000L, Date.valueOf("2020-12-12"), "x7", TypesOfExpenses.OTHER);
        Transaction testTransaction8 = new Transaction(2, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT);

        TreeSet<Transaction> testTransactionSet = new TreeSet<>(new TransactionComparator());
        testTransactionSet.add(testTransaction1);
        testTransactionSet.add(testTransaction2);
        testTransactionSet.add(testTransaction3);
        testTransactionSet.add(testTransaction4);
        testTransactionSet.add(testTransaction5);
        testTransactionSet.add(testTransaction6);
        testTransactionSet.add(testTransaction7);
        testTransactionSet.add(testTransaction8);
        /*for (Transaction t : testTransactionSet){
            System.out.println(t.getPurpose());
        }*/
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
        sorterService.getSortMap().get("type").map(Transaction::getPurpose).forEach(System.out::println);
        return true;
    }
}
