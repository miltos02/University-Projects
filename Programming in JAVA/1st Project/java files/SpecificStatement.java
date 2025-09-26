public class SpecificStatement extends Statement {

    public SpecificStatement(Employee empl, double specific){
        super(empl);
        this.amount = specific;
    }

    public double getAmount(){
        return amount;
    }

    public String toString(){
        return "Specific     Statement | "+super.toString();
    }

}
