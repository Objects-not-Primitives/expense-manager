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

}
