public abstract class Statement {
    protected Employee empl;
    protected double amount;

    public Statement(Employee empl){
        this.empl = empl;
    }

    public Employee getEmployee(){
        return empl;
    }

    public abstract double getAmount();

    public String toString() { return  "Amount: " + (Math.round(amount * 100)) / 100.0; }
}
