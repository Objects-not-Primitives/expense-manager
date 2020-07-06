public class Employee {

    private int id;
    private String vacancyName;
    private int salary;

    public Employee(int id, String vacancyName, int salary) {
        this.id = id;
        this.vacancyName = vacancyName;
        this.salary = salary;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Employee employee = (Employee) obj;
        return salary == employee.salary &&
                id == employee.id &&
                vacancyName.equals(employee.vacancyName);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
