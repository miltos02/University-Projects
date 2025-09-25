public class Difference extends Statement{

    public Difference(Employee empl, double diff){
        super(empl);
        this.amount = diff;
    }

    public double getAmount() {
        return -amount;
    }

    public String toString(){
        return "Difference   Statement | "+super.toString();
    }
}
