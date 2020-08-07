package org.objectsNotPrimitives.expenseManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestTransactionDAO {
    private static final String propertiesPath = "application.properties";
    private static final String deleteDBPath = "src\\main\\resources\\deleteDB.sql";
    private static final String createDBPath = "src\\main\\resources\\createDB.sql";
    private static final String initDBPath = "src\\main\\resources\\initDB.sql";

    public static void main(String[] args) throws SQLException, IOException {
        testConnect();
        testDAO();
    }

    private static boolean testDAO() throws SQLException, IOException {
        execScripts();
        Properties property = PropertyLoader.load(propertiesPath);

        try (TransactionDAO transactionDAO = TransactionDAO.getInstance(property)) {
            List<Transaction> testTransactionList = new ArrayList<>();
            Transaction testTransaction1 = new Transaction(11, 2500L, Date.valueOf("2020-12-12"), "xz", TypesOfExpenses.FOOD);
            Transaction testTransaction2 = new Transaction(12, 2501L, Date.valueOf("2020-12-13"), "xz2", TypesOfExpenses.OTHER);
            Transaction testTransaction3 = new Transaction(13, 2502L, Date.valueOf("2020-12-13"), "xz3", TypesOfExpenses.ENTERTAINMENT);
            Transaction testTransaction4 = new Transaction(4, 2503L, Date.valueOf("2020-12-13"), "xz4", TypesOfExpenses.OTHER);

            testTransactionList.add(testTransaction1);
            testTransactionList.add(testTransaction2);
            testTransactionList.add(testTransaction3);

            transactionDAO.insertRecord(testTransaction1);
            transactionDAO.insertRecord(testTransaction2);
            transactionDAO.insertRecord(testTransaction3);
            transactionDAO.updateRecord(testTransaction4);
            transactionDAO.deleteRecord(5);
            transactionDAO.updateTypeRecord(testTransactionList,TypesOfExpenses.FOOD.getTypesOfExpenses());
            transactionDAO.deleteTypeRecord(TypesOfExpenses.OTHER.getTypesOfExpenses());

            List<Transaction> testTransactionListOneType = transactionDAO.selectOneType(TypesOfExpenses.ENTERTAINMENT.getTypesOfExpenses());
            List<Transaction> testTransactionListOtherType = transactionDAO.selectOneType(TypesOfExpenses.FOOD.getTypesOfExpenses());
            testTransactionListOneType.addAll(testTransactionListOtherType);
            List<Transaction> summaryTransactionList = new ArrayList<>(transactionDAO.selectAll());

            if (testTransactionListOneType.containsAll(summaryTransactionList)){
                System.out.println("Методы DAO работают нормально");
                return true;
            } else {
                System.out.println("Методы DAO не работают");
                return false;
            }
        }
    }

    private static void execScripts() throws IOException {
        Properties properties = PropertyLoader.load(propertiesPath);
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        scriptExecutor.executeSQL(deleteDBPath, properties);
        scriptExecutor.executeSQL(createDBPath, properties);
        scriptExecutor.executeSQL(initDBPath,properties);
    }

    private static boolean testConnect(){
        Properties property = PropertyLoader.load(propertiesPath);
        try (Connection con = TransactionDAO.connect(property)) {
            System.out.println("Подключение к БД установлено");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Подключение к БД отсутствует");
            return false;
        }
    }
}