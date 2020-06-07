public class Worker {
    int salary;
    String vacancyName;
    int id;

    public int getId() {
        return id;
    }

    public Worker(int id, String vacancyName, int salary) {
        this.salary = salary;
        this.vacancyName = vacancyName;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Worker() {
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }
}
