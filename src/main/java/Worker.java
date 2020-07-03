public class Worker {
    private int salary;
    private String vacancyName;
    private int id;

    public int getId() {
        return id;
    }

    public Worker(int id, String vacancyName, int salary) {
        this.salary = salary;
        this.vacancyName = vacancyName;
        this.id = id;
    }

    public Worker() {
    }

    public int getSalary() {
        return salary;
    }

    public String getVacancyName() {
        return vacancyName;
    }

}
