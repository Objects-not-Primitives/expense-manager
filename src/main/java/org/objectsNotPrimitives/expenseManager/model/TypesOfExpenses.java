package org.objectsNotPrimitives.expenseManager.model;

public enum TypesOfExpenses {
    FOOD("FOOD"),
    ENTERTAINMENT("ENTERTAINMENT"),
    OTHER("OTHER");

    private String type;

    TypesOfExpenses(String type) {
        this.type = type;
    }

    public String getTypesOfExpenses() {
        return type;
    }
}
