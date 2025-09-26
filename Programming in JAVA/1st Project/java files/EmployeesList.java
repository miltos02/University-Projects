import java.util.ArrayList;

public class EmployeesList {

    private final ArrayList<Employee> employees = new ArrayList<>();

    public void newEmployee(Employee empl){
        employees.add(empl);
    }

    public Employee getEmployee(int i){
        return employees.get(i);
    }

    public int numOfEmployees(){return employees.size();}

    public String toString () {
        String emplPrinted = "";
        int i = 1;
        for (Employee empl : employees) {
            emplPrinted += "\t\t\t"+i + ". " + empl.toString() + "\n";
            i++;
        }
        return emplPrinted;
    }

}
