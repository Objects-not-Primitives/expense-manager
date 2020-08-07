package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.utils.TransactionComparator;

import java.sql.Date;
import java.util.TreeSet;

public class TestTransactionComparator {
    public static void main(String[] args){
        testComparator();
    }

    private static boolean testComparator(){
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
        return true;
    }
}
