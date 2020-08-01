package org.objectsNotPrimitives.expenseManager;

import java.sql.Date;
import java.util.Objects;

public class Transaction {

    private int id;
    private long value;
    private Date date;
    private String purpose;

    public Transaction(int id, long value, Date date, String purpose) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.purpose = purpose;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public String getPurpose() {
        return purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return id == transaction.id &&
                value == transaction.value &&
                date.equals(transaction.date)&&
                purpose.equals(transaction.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date, purpose);
    }
}
