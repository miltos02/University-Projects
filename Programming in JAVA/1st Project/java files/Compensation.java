public class Compensation extends Statement{
    private final ExpenseType type;

    public Compensation(Employee empl, ExpenseType type){
        super(empl);
        this.type = type;
        this.amount = empl.getEmployee_sExpenses().sumOfCompPerType(type);
    }

    public ExpenseType getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    public String toString(){
        return "Compensation Statement | Type: "+type.getDescription()+" | "+super.toString();
    }
}
