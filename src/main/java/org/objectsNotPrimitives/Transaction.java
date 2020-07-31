package org.objectsNotPrimitives;

import java.util.Objects;

public class Transaction {

    private int id;
    private String vacancyName;
    private int salary;

    public Transaction(int id, String vacancyName, int salary) {
        this.id = id;
        this.vacancyName = vacancyName;
        this.salary = salary;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return id == transaction.id &&
                salary == transaction.salary &&
                vacancyName.equals(transaction.vacancyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancyName, salary);
    }
}
