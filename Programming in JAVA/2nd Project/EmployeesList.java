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

    public int findEmployeeByCode(int code){
        int i = -1;
        for(Employee empl: employees){
            if(empl.getCode() == code){
                i = employees.indexOf(empl);
                break;
            }
        }
        return i;
    }

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
