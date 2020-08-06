package org.objectsNotPrimitives.expenseManager;

import java.util.*;
import java.util.stream.*;

public class SorterService {

    private final HashMap<String, Stream<Transaction>> sortMap;

    public SorterService(List<Transaction> transactionList) {
        HashMap<String, Stream<Transaction>> stringFunctionHashMap = new HashMap<>();
        stringFunctionHashMap.put("value", transactionList.stream().sorted(Comparator.comparing(Transaction::getValue)));
        stringFunctionHashMap.put("date", transactionList.stream().sorted(Comparator.comparing(Transaction::getDate)));
        stringFunctionHashMap.put("type", transactionList.stream().sorted(Comparator.comparing(Transaction::getType)));
        this.sortMap = stringFunctionHashMap;
    }

    public HashMap<String, Stream<Transaction>> getSortMap() {
        return sortMap;
    }
}
