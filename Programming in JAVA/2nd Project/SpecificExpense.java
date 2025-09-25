public class SpecificExpense{
    private final Employee empl;
    private final ExpenseType specificType;
    private final double valueQuantity;
    private final String justification;

    public SpecificExpense(Employee empl, ExpenseType specificType, double valueQuantity, String justification){
        this.empl = empl;
        this.specificType = specificType;
        this.valueQuantity = valueQuantity;
        this.justification = justification;
    }


    public double getValueQuantity(){
        return valueQuantity;
    }

    public ExpenseType getSpecificType(){
        return specificType;
    }

    public Employee getEmployee(){return  empl;}

    public String getJustification(){return justification;}

    public double calcComp(){
        return specificType.calcComp(valueQuantity);
    } //calls calcComp of ExpenseType

    public String toString(){
        String vOrq =  specificType instanceof ExpenseType2 ? " | Value: " : " | Quantity: ";
        return specificType.toString()+vOrq+valueQuantity+" | Justification: "+justification;
    }

}