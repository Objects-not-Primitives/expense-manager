package org.objectsNotPrimitives.expenseManager;

import org.junit.Assert;
import org.junit.Test;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.service.SorterService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestSorterService {

    private static final SorterService sorterService = new SorterService();
    private static final List<Transaction> initTransactionList = List.of(
                new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD),
                new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER),
                new Transaction(12, 25000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD),
                new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER),
                new Transaction(3, 35000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT),
                new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT));

    @Test
    public void testSorterService1() {
        List<Transaction> expectedTypeTransactionList= new ArrayList<>();
        expectedTypeTransactionList.add(new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD));
        expectedTypeTransactionList.add(new Transaction(12, 25000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD));
        expectedTypeTransactionList.add(new Transaction(3, 35000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT));
        expectedTypeTransactionList.add(new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT));
        expectedTypeTransactionList.add(new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER));
        expectedTypeTransactionList.add(new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER));
        List<Transaction> actualTransactionList = initTransactionList.stream()
                .sorted(sorterService.getComparator("type"))
                .collect(Collectors.toList());
        Assert.assertEquals(actualTransactionList, expectedTypeTransactionList);
    }

    @Test
    public void testSorterService2() {
        List<Transaction> expectedDateTransactionList= new ArrayList<>();
        expectedDateTransactionList.add(new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD));
        expectedDateTransactionList.add(new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER));
        expectedDateTransactionList.add(new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER));
        expectedDateTransactionList.add(new Transaction(3, 35000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT));
        expectedDateTransactionList.add(new Transaction(12, 25000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD));
        expectedDateTransactionList.add(new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT));
        List<Transaction> actualTransactionList = initTransactionList.stream()
                .sorted(sorterService.getComparator("date"))
                .collect(Collectors.toList());
        Assert.assertEquals(actualTransactionList, expectedDateTransactionList);
    }

    @Test
    public void testSorterService3() {
        List<Transaction> expectedValueTransactionList= new ArrayList<>();
        expectedValueTransactionList.add(new Transaction(12, 25000L, Date.valueOf("2020-12-15"), "x5", TypesOfExpenses.FOOD));
        expectedValueTransactionList.add(new Transaction(3, 35000L, Date.valueOf("2020-12-14"), "x4", TypesOfExpenses.ENTERTAINMENT));
        expectedValueTransactionList.add(new Transaction(9, 250000L, Date.valueOf("2020-12-13"), "x1", TypesOfExpenses.FOOD));
        expectedValueTransactionList.add(new Transaction(5, 250000L, Date.valueOf("2020-12-14"), "x3", TypesOfExpenses.OTHER));
        expectedValueTransactionList.add(new Transaction(7, 350000L, Date.valueOf("2020-12-13"), "x2", TypesOfExpenses.OTHER));
        expectedValueTransactionList.add(new Transaction(1, 350000L, Date.valueOf("2020-12-15"), "x8", TypesOfExpenses.ENTERTAINMENT));
        List<Transaction> actualTransactionList = initTransactionList.stream()
                .sorted(sorterService.getComparator("value"))
                .collect(Collectors.toList());
        Assert.assertEquals(actualTransactionList, expectedValueTransactionList);
    }
}