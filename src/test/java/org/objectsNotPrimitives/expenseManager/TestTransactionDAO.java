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

    public static void main(String[] args) throws SQLException, IOException {
        testConnect();
        testDAO();
    }

    private static boolean testDAO() throws SQLException, IOException {
        execScripts();
        Properties property = PropertyLoader.load(propertiesPath);

        try (TransactionDAO transactionDAO = TransactionDAO.getInstance(property)) {
            Transaction testTransaction1 = new Transaction(1,250000,Date.valueOf("2020-12-12"),"xz");
            Transaction testTransaction2 = new Transaction(2, 250000,Date.valueOf("2020-12-13"),"xz");
            Transaction testTransaction2New = new Transaction(2, 350000,Date.valueOf("2020-12-14"),"up");
            Transaction testTransaction3Deleted = new Transaction(3, 250000,Date.valueOf("2020-12-15"),"xz");

            List<Transaction> testTransactionList = new ArrayList<>();
            testTransactionList.add(testTransaction1);
            testTransactionList.add(testTransaction2New);
            transactionDAO.insertRecord(testTransaction1);
            transactionDAO.insertRecord(testTransaction2);
            transactionDAO.insertRecord(testTransaction3Deleted);
            transactionDAO.updateRecord(testTransaction2New);
            transactionDAO.deleteRecord(testTransaction3Deleted.getId());

            List<Transaction> testTransactionListDB = transactionDAO.selectAll();
            if (testTransactionList.get(0).equals(transactionDAO.selectOne(1).get()) && testTransactionList.equals(testTransactionListDB)) {
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
    }

    private static boolean testConnect() throws IOException {
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
