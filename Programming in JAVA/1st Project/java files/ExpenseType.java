import java.util.ArrayList;
import java.util.Random;

public abstract class ExpenseType{
    protected int code;
    protected String description;
    protected double maxComp;

    static ArrayList<Integer> allCodes = new ArrayList<>(); //holds the codes 

    public ExpenseType( String description, double maxComp){
		//Creation of the new random 5 digit code 
        Random ran =new Random();
        int i;
        do {
             i = 10000 + ran.nextInt(10000);
        } while (allCodes.contains(i));
        allCodes.add(i);

        this.code = i;
        this.description = description;
        this.maxComp = maxComp;
    }


    public String getDescription(){
        return description;
    }


    public int getCode(){
        return code;
    }

    public double getMaxComp() {
        return maxComp;
    }

    public abstract double calcComp(double vq); //calculates compensation amount for expense
    public String toString(){
        return "Code: " + code + " | Description: " + description + " | Maximum Compensation: " + maxComp;
    }

}