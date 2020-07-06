import java.util.HashSet;
import java.util.Set;

public class TestEmployeeEquals {

    public static void main(String[] args) {
        TestEmployeeEquals.employeeEqualsTest();
    }

    private static void employeeEqualsTest( ){
        Set<Employee> employeeSet = new HashSet<>();

        Employee employee1 = new Employee(1, "lol", 100);
        Employee employee2 = new Employee(1, "lol", 100);

        employeeSet.add(employee1);
        employeeSet.add(employee2);

        int expectedSize = 1;

        if (employeeSet.size() == expectedSize) {
            System.out.println("employee equals test passed");
        } else  {
            System.out.println("employee equals test failed, expected size of set was "
                    + expectedSize + " but actually is " + employeeSet.size());
        }
    }


}
