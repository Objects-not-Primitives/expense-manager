public class Employee {

    private int salary;
    private String vacancyName;
    private int id;

    public int getId() {
        return id;
    }

    public Employee(int id, String vacancyName, int salary) {
        this.salary = salary;
        this.vacancyName = vacancyName;
        this.id = id;
    }

    public Employee() {
    }

    public int getSalary() {
        return salary;
    }

    public String getVacancyName() {
        return vacancyName;
    }
}
