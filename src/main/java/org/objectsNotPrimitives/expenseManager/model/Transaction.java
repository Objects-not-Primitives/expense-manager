package org.objectsNotPrimitives.expenseManager.model;

import java.sql.Date;
import java.util.Objects;

public class Transaction {

    private Integer id;
    private Long value;
    private Date date;
    private String purpose;
    private TypesOfExpenses type;

    public Transaction(Integer id, Long value, Date date, String purpose, TypesOfExpenses type) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.purpose = purpose;
        this.type = type;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public Long getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public String getPurpose() {
        return purpose;
    }

    public TypesOfExpenses getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return id.equals(transaction.id) &&
                value.equals(transaction.value) &&
                date.equals(transaction.date) &&
                purpose.equals(transaction.purpose) &&
                type.equals(transaction.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date, purpose, type);
    }
}

