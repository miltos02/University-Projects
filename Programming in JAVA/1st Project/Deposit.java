public class Deposit extends Statement{

    public Deposit(Employee empl, double dep){
        super(empl);
        this.amount = dep;
    }
    public double getAmount(){
        return -amount;
    }

    public String toString(){
        return "Deposit      Statement | "+super.toString();
    }
}
