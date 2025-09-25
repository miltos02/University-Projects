import java.io.*;
import java.util.*;

public class ExpenseType1 extends ExpenseType{
    private final double pricePerUnit;
    private final String unitMeasure;

    public ExpenseType1( String description, double maxComp, double pricePerUnit, String unitMeasure){
        super( description, maxComp);
        this.type = 1;
        this.pricePerUnit = pricePerUnit;
        this.unitMeasure = unitMeasure;
    }

	//used during the reading of the files
    public ExpenseType1(int code, String description, double maxComp, double pricePerUnit, String unitMeasure){
        super(code, description,maxComp);
        this.type = 1;
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

    public static ExpenseType parse(BufferedReader buff, int i){
        int code = -1;
        String descr = "";
        double maxComp = 100;     //predetermined value
        double ppu = 10;         //predetermined value
        String  unit = "units"; //predetermined value

        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;
            line = buff.readLine();

            while ( !(line.trim().equals("}")) ) {
                lineTokens = new StringTokenizer(line);

                if(lineTokens.countTokens()>=1) { //checking if line is empty
                    token = lineTokens.nextToken();

                    if (token.toUpperCase().equals("CODE")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();

                            if(mainApp.dataValidityInt(token)) { //verification test
                                code = Integer.parseInt(token);
                            }else
                                System.out.println("\tInvalid Code. Expense type ["+ i +"] cannot be resolved");
                        } else
                            System.out.println("Code not found. Expense type ["+ i +"] cannot be resolved");

                    } else if (token.toUpperCase().equals("DECSR")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            descr = token;
                        } else
                            System.out.println("\tDescription not found. Expense type ["+ i +"] cannot be resolved");

                    } else if (token.equals("MAXCOMP")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityDouble(token)) {  //verification test
                                maxComp = Double.parseDouble(token);
                            }
                        }

                    } else if (token.equals("PPU")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityDouble(token)) {  //verification test
                                ppu = Double.parseDouble(token);
                            }
                        }

                    } else if (token.equals("UNIT")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            unit = token;
                        }
                    }
                }

                line = buff.readLine();
            }
        } catch (IOException ex) {
            System.err.println("Error reading expense types' file.");
        }
        if (code!=-1 &&  !descr.equals("")) {
            ExpenseType1 exp = new ExpenseType1( code, descr, maxComp, ppu, unit);
            return (exp);
        }else
            return null;
    }
}
