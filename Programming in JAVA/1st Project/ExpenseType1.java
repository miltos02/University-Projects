public class ExpenseType1 extends ExpenseType{
    private final double pricePerUnit;
    private final String unitMeasure;

        public ExpenseType1( String description, double maxComp, double pricePerUnit, String unitMeasure){
        super( description, maxComp);
        this.pricePerUnit = pricePerUnit;
        this.unitMeasure = unitMeasure;
    }
    public double getPricePerUnit(){
            return pricePerUnit;
    }
    
    public String getUnitMeasure(){ return unitMeasure; }

    public double calcComp(double vq){
            return pricePerUnit * vq;
    } //calculates the compensation amount for the expense

}