import java.util.Objects;

public class Employee {

    private int id;
    private String vacancyName;
    private int salary;

    public Employee(int id, String vacancyName, int salary) {
        this.id = id;
        this.vacancyName = vacancyName;
        this.salary = salary;
    }
    public Employee(){}

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
        Employee employee = (Employee) o;
        return id == employee.id &&
                salary == employee.salary &&
                vacancyName.equals(employee.vacancyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancyName, salary);
    }
}
