package org.objectsNotPrimitives.expenseManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SorterService {
    public static List<Transaction> sortBy (String firstParam, String secondParam, List<Transaction> transactionList){
        String switchParam =firstParam+secondParam;
        List<Transaction> sortedTransactionList = new ArrayList<>();
        switch (switchParam){
            case ("valuedate"):
                sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getValue).thenComparing(Transaction::getDate)
                ).collect(Collectors.toList());
                break;
            case ("valuetype"):
                sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getValue).thenComparing(Transaction::getType)
                ).collect(Collectors.toList());
                break;
            case ("datevalue"):
               sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getValue)
                ).collect(Collectors.toList());
                break;
            case ("datetype"):
                sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getType)
                ).collect(Collectors.toList());
                break;
            case ("typedate"):
                sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getType).thenComparing(Transaction::getDate)
                ).collect(Collectors.toList());
                break;
            case ("typevalue"):
                sortedTransactionList = transactionList.stream().sorted(
                        Comparator.comparing(Transaction::getType).thenComparing(Transaction::getValue)
                ).collect(Collectors.toList());
                break;
        }
    return sortedTransactionList;
    }

}
