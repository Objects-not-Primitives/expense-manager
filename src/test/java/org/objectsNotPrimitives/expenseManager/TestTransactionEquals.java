package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;

import java.sql.Date;
import org.junit.*;

public class TestTransactionEquals {

  @Test
    public void employeeEqualsTest() {
        Transaction transaction1 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Transaction transaction2 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Assert.assertEquals(transaction1, transaction2);
    }

    @Test
    public void employeeEqualsTest1() {
        Transaction transaction1 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Transaction transaction2 = new Transaction(2, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Assert.assertEquals(transaction1, transaction2);
    }

    @Test
    public void employeeEqualsTest2() {
        Transaction transaction1 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Transaction transaction2 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Assert.assertEquals(transaction1, transaction2);
    }

    @Test
    public void employeeEqualsTest3() {
        Transaction transaction1 = new Transaction(1, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Transaction transaction2 = new Transaction(2, 250000L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.OTHER);
        Assert.assertEquals(transaction1, transaction2);
    }
}
