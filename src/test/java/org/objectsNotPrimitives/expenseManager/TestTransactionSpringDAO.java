package org.objectsNotPrimitives.expenseManager;

import org.objectsNotPrimitives.expenseManager.dao.TransactionSpringDAO;
import org.objectsNotPrimitives.expenseManager.model.Transaction;
import org.objectsNotPrimitives.expenseManager.dao.TransactionDAO;
import org.objectsNotPrimitives.expenseManager.model.TypesOfExpenses;
import org.objectsNotPrimitives.expenseManager.utils.PropertyLoader;
import org.objectsNotPrimitives.expenseManager.utils.ScriptExecutor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
    public static void main(String[] args) throws SQLException, IOException {
        testConnect();
        testDAO();
    }

    private static boolean testDAO() throws SQLException, IOException {

        execScripts();
        Properties property = PropertyLoader.load(propertiesPath);
        TransactionSpringDAO transactionSpringDAO = context.getBean("transactionSpringDAO", TransactionSpringDAO.class);
        TransactionDAO transactionDAO = TransactionDAO.getInstance(property);
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
        transactionDAO.updateTypeRecord(testTransactionList.stream(), TypesOfExpenses.FOOD.getTypesOfExpenses());
        transactionSpringDAO.deleteTypeRecord(TypesOfExpenses.OTHER.getTypesOfExpenses());
            List<Transaction> testTransactionListOneType = transactionSpringDAO.selectOneType(TypesOfExpenses.ENTERTAINMENT.getTypesOfExpenses())
                    .collect(Collectors.toList());
            List<Transaction> testTransactionListOtherType = transactionSpringDAO.selectOneType(TypesOfExpenses.FOOD.getTypesOfExpenses())
                    .collect(Collectors.toList());
            testTransactionListOneType.addAll(testTransactionListOtherType);
            List<Transaction> summaryTransactionList = transactionSpringDAO.selectAll().collect(Collectors.toList());

            if (testTransactionListOneType.containsAll(summaryTransactionList)) {
                System.out.println("DAO methods test passed");
                return true;
            } else {
                System.out.println("DAO methods test not passed");
                return false;
            }

    }

    private static void execScripts() throws IOException {
        Properties properties = PropertyLoader.load(propertiesPath);
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        assert properties != null;
        scriptExecutor.executeSQL(deleteDBPath, properties);
        scriptExecutor.executeSQL(createDBPath, properties);
        scriptExecutor.executeSQL(initDBPath, properties);
    }

    private static boolean testConnect() {
        Properties property = PropertyLoader.load(propertiesPath);
        try {   TransactionSpringDAO transactionSpringDAO = context.getBean("transactionSpringDAO", TransactionSpringDAO.class);
            System.out.println("Connected to the database");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is no connection to the database");
            return false;
        }
    }
}
