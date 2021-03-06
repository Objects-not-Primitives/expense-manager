package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.dao.TransactionSpringDAO;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.utils.PropertyLoader;
import org.objectsNotPrimitives.expenseManager.utils.ScriptExecutor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.junit.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class TestTransactionSpringDAO {
    private static final String propertiesPath = "application.properties";
    private static final String deleteDBPath = "src\\main\\resources\\deleteDB.sql";
    private static final String createDBPath = "src\\main\\resources\\createDB.sql";
    private static final String initDBPath = "src\\main\\resources\\initDB.sql";
    private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml"
    );

    @Test
    public void testDAO() throws IOException {
        execScripts();
        TransactionSpringDAO transactionSpringDAO = context.getBean("transactionSpringDAO", TransactionSpringDAO.class);
        List<Transaction> testTransactionList = List.of(
                new Transaction(11, 2500L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.FOOD),
                new Transaction(12, 2501L, Date.valueOf("2020-12-13"), "xz2", TypesOfExpenses.OTHER),
                new Transaction(13, 2502L, Date.valueOf("2020-12-13"), "xz3", TypesOfExpenses.ENTERTAINMENT),
                new Transaction(4, 2503L, Date.valueOf("2020-12-13"), "xz4", TypesOfExpenses.OTHER)
        );

        transactionSpringDAO.insertRecord(testTransactionList.get(0));
        transactionSpringDAO.insertRecord(testTransactionList.get(1));
        transactionSpringDAO.insertRecord(testTransactionList.get(2));
        transactionSpringDAO.updateRecord(testTransactionList.get(3));
        transactionSpringDAO.deleteRecord(5);
        transactionSpringDAO.deleteTypeRecord(TypesOfExpenses.OTHER.getTypesOfExpenses());
        List<Transaction> testTransactionListOneType = transactionSpringDAO.selectOneType(TypesOfExpenses.ENTERTAINMENT.getTypesOfExpenses())
                .collect(Collectors.toList());
        List<Transaction> testTransactionListOtherType = transactionSpringDAO.selectOneType(TypesOfExpenses.FOOD.getTypesOfExpenses())
                .collect(Collectors.toList());
        testTransactionListOneType.addAll(testTransactionListOtherType);
        List<Transaction> summaryTransactionList = transactionSpringDAO.selectAll().collect(Collectors.toList());
        Assert.assertTrue("DAO methods test passed", testTransactionListOneType.containsAll(summaryTransactionList));

    }

    private static void execScripts() throws IOException {
        Properties properties = PropertyLoader.load(propertiesPath);
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        assert properties != null;
        scriptExecutor.executeSQL(deleteDBPath, properties);
        scriptExecutor.executeSQL(createDBPath, properties);
        scriptExecutor.executeSQL(initDBPath, properties);
    }

    @Test
    public void testConnect() {
        context.getBean("transactionSpringDAO", TransactionSpringDAO.class);
    }
}
