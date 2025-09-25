import java.util.ArrayList;

public class ExpenseTypeList {

    private final ArrayList<ExpenseType> expenseTypes = new ArrayList<>();

    public void newExpenseType(ExpenseType type){
        expenseTypes.add(type);
    }

    public ExpenseType getExpenseType(int i){
        return expenseTypes.get(i);
    }

    public int numOfExpTypes(){return expenseTypes.size();}

    public String toString(){
        String typesPrinted = "\n";
        int i = 1;
        for(ExpenseType type: expenseTypes){
            typesPrinted += "\t\t\t"+i + ". " + type.toString() +"\n";
            i++;
        }
        return typesPrinted;
    }

}
