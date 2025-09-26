public  class Employee{
    private final String lastname;
    private final String firstname;
    private final double genMaxComp;
    private final SpecificExpenseList employee_sExpenses;
    private final StatementList employee_sStatements;

    public Employee(String lastname, String firstname, double genMaxComp){
        this.lastname = lastname;
        this.firstname = firstname;
        this.genMaxComp = genMaxComp;
        this.employee_sExpenses = new SpecificExpenseList();
        this.employee_sStatements = new StatementList();
    }
    public String getLastName(){
        return lastname;
    }

    public String getFirstName(){
        return firstname;
    }

    public double getGenMaxComp(){
        return genMaxComp;
    }

    public SpecificExpenseList getEmployee_sExpenses(){
        return employee_sExpenses;
    }

    public StatementList getEmployee_sStatements(){
        return employee_sStatements;
    }

    public int numOfExpenses(){return employee_sExpenses.numOfExpenses();}

    public int numOfStatements(){return employee_sStatements.numOfStatements();}

    public void newExpense(SpecificExpense exp){employee_sExpenses.newExpense(exp);}

    public void newStatement(Statement st){employee_sStatements.newStatement(st);}

    public void deleteStatement(int i){employee_sStatements.deleteStatement(i);}

    private double sumOfCompensations(ExpenseTypeList expenseTypes){ //used in liquidation
        boolean flag;
        ExpenseType type;
		//checking, for each expense type, if the employee has expenses of this type
        for (int i=0; i<expenseTypes.numOfExpTypes(); i++){
            type = expenseTypes.getExpenseType(i);
            flag = false;
            int j = 0;
            while( j < this.numOfExpenses() && (!flag) ){
                if(type == this.getEmployee_sExpenses().getSpecificExpense(j).getSpecificType()){
                    flag = true;
                }
                j++;
            }
			//creation of the compensation statements for each expense type
            if(flag){
                Compensation compSt = new Compensation(this, type);
                this.newStatement(compSt);
            }
        }
        //calculation of the compensation's total statements
        double sum = 0.0;
        for(int i=0; i<employee_sStatements.numOfStatements(); i++){
            Statement item = employee_sStatements.getStatement(i);
            if(item instanceof Compensation){
                sum += item.getAmount();
            }
        }
        return sum;
    }


    private void calcDiff(double sum){ //used in liquidation
		//calculation and creation of difference statement
        double diff = sum - this.getGenMaxComp();
        if(diff>0){
            Difference diffStatement = new Difference(this, diff);
            this.newStatement(diffStatement);
        }
    }

    public void liquidation(ExpenseTypeList expenseTypes){ //called in Clearance of mainApp 
        double sum = sumOfCompensations(expenseTypes);
        calcDiff(sum);
        double sumGeneral = 0.0;
        for(int i=0; i<employee_sStatements.numOfStatements(); i++){
            Statement item = employee_sStatements.getStatement(i);
            sumGeneral += item.getAmount();
        }
		//creation of specific statement
        SpecificStatement specStatement = new SpecificStatement(this, sumGeneral);
        this.newStatement(specStatement);
    }


    public String toString(){ return   firstname + " " + lastname + " | Maximum possible Compensation (per month): " + genMaxComp; }

}