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

    public int findExpTypeByCode(int code){
        int i = -1;
        for(ExpenseType exp: expenseTypes){
            if(exp.getCode() == code){
                i = expenseTypes.indexOf(exp);
                break;
            }
        }
        return i;
    }

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
