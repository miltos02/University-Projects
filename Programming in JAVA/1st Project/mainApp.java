import java.util.Scanner;

/*************************************************************
 *  Team Number:            5
 *
 *	1st Student Name:	    MILTIADHS TSOLKAS
 *
 *  2nd Student Name:       ALVIONA MANTSO
 *************************************************************/

public class mainApp {

    static Scanner input = new Scanner(System.in);

    static ExpenseTypeList expenseTypes = new ExpenseTypeList();
    static EmployeesList employees = new EmployeesList();

    static SpecificExpenseList allExpenses = new SpecificExpenseList(); //general list of expenses (for all employees)
    static StatementList allStatements = new StatementList(); //General list of expenses (for all employees)

    public static void main (String[] args) {
		//Initialization of the list of the types of expenses:
        expenseTypes.newExpenseType(new ExpenseType1( "Highlighter", 10, 1.59, "units" ));
        expenseTypes.newExpenseType(new ExpenseType1( "Uniform    ", 100, 50, "units"));
        expenseTypes.newExpenseType(new ExpenseType1( "Manual     ", 300, 70, "units"));
        expenseTypes.newExpenseType(new ExpenseType2( "Taxi       ", 50, 0.8));
        expenseTypes.newExpenseType(new ExpenseType2( "Hotel      ", 500, 0.8));
        expenseTypes.newExpenseType(new ExpenseType2( "Trip       ", 1000, 0.9));
        expenseTypes.newExpenseType(new ExpenseType1( "Diet       ", 100,30,"day"));
        expenseTypes.newExpenseType(new ExpenseType1( "Travel     ", 100,1.5,"km"));


		//Initialization of the list of the employees
        employees.newEmployee(new Employee("Mancho      ", "Alviona", 1000));
        employees.newEmployee(new Employee("Tsolkas     ", "Miltos ", 830));
        employees.newEmployee(new Employee("Mpalampanh  ", "Marlen ", 650));
        employees.newEmployee(new Employee("Goniotakhs  ", "Kwstas ", 960));
        employees.newEmployee(new Employee("Papadopoulos", "Spyros ", 200));

		//Initialization of the employees' expenses lists
        Employee empl = employees.getEmployee(0);
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(0), 4, "Notes" ));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(0), 2, "Notes" ));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(1), 1, "Clothing"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(5), 700, "Meeting"));

        empl.newStatement(new Deposit(empl, 564)); //Initialization of the Statements list 

        empl = employees.getEmployee(1);
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(2), 2, "Study"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(3), 20, "Transport"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(2), 1, "Study"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(0), 5, "Notes"));

        empl.newStatement(new Deposit(empl, 670)); //Initialization of the specific employee's deposit lists

        empl = employees.getEmployee(2);
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(4), 100, "Stay"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(0), 4, "Notes"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(1), 2, "Clothing"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(5), 600, "Meeting"));

        empl.newStatement(new Deposit(empl, 807)); //Initialization of the specific employee's deposit lists

        empl = employees.getEmployee(3);
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(0), 1, "Notes"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(3), 25, "Transport"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(1), 1, "Clothing"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(3), 20, "Commuting"));

        empl.newStatement(new Deposit(empl, 254)); //Initialization of the specific employee's deposit lists

        empl = employees.getEmployee(4);
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(7), 30, "TV-Show"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(7), 20, "TV-Show"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(6), 4, "TV-Show"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(3), 30, "TV-Show"));
        empl.newExpense(new SpecificExpense(empl, expenseTypes.getExpenseType(3),50,"TV-Show"));

        empl.newStatement(new Deposit(empl,150));

        //Initialization of the general expenses lists
        for (int i=0; i < employees.numOfEmployees(); i++){
            empl = employees.getEmployee(i);
            for (int j=0; j < empl.numOfExpenses(); j++ ) {
               allExpenses.newExpense(empl.getEmployee_sExpenses().getSpecificExpense(j));
            }
        }

        //Initialization of the general statements lists
        for (int i=0; i < employees.numOfEmployees(); i++){
            empl = employees.getEmployee(i);
            for (int j=0; j < empl.numOfStatements(); j++ ) {
                allStatements.newStatement(empl.getEmployee_sStatements().getStatement(j));
            }
        }

        int answer;
        do {
            System.out.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
            System.out.println(" 1. Insert new expense type;" +
                    "\n 2. Insert new employee;" +
                    "\n 3. Insert an employee's expense;" +
                    "\n 4. Insert an employee's deposit;" +
                    "\n 5. Show all eployees' expenses;" +
                    "\n 6. Compensate an employee for their expenses;" +
                    "\n 7. Show an employee's statements;" +
                    "\n 8. Compensate all employees for their expenses;" +
                    "\n 9. Show the final compensation of all employees;" +
                    "\n 10.Exit;"+
                    "\n-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
            System.out.print(">Enter number for the desired operation: ");
            answer = inputValidityInt(input.next(),10); //validation test
            System.out.println();
            switch (answer) {
                case 1:
                    procedure01();
                    break;
                case 2:
                    procedure02();
                    break;
                case 3:
                    procedure03();
                    break;
                case 4:
                    procedure04();
                    break;
                case 5:
                    procedure05();
                    break;
                case 6:
                    procedure06();
                    break;
                case 7:
                    procedure07();
                    break;
                case 8:
                    procedure08();
                    break;
                case 9:
                    procedure09();
                    break;
            }
            System.out.println("\n\n");

        } while (answer != 10);
    }//main


    static void procedure01() {
        System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
        System.out.println("\t>Insert new expense type");

        System.out.print("\t\tDescription: ");
        String desc = input.next();
        if (desc.equals("-1")){return; } //return to the main menu

        System.out.print("\t\tMaximum possible amount of Compensation (give 0 if this expense type has no upper bound): ");
        double mc = inputValidityDouble(input.next()); //validation test
        if (mc == -1){return; } //return to the main menu

        System.out.print("\t\tGive expense type: " +
                "\n\t\t\t1.Quantity X Price" +
                "\n\t\t\t2.Value X Compensation percentage" +
                "\n\t\t\tEnter: ");
        int type = inputValidityInt(input.next(), 2); //validation test
        if (type == -1){return; } //return to the main menu

        if (type == 1) {
            System.out.print("\t\tPrice per Unit: ");
            double ppu = inputValidityDouble(input.next()); //validation test
            if (ppu == -1){return; } //return to the main menu

            System.out.print("\t\tUnit Measure: ");
            String um = input.next();
            if (um.equals("-1")){return; } //return to the main menu

            expenseTypes.newExpenseType(new ExpenseType1( desc, mc, ppu, um));

        } else {
            System.out.print("\t\tPercentage: ");
            double perc = percentageValidity(input.next()); //validation test
            if (perc == -1){return; } //return to the main menu

            expenseTypes.newExpenseType(new ExpenseType2( desc, mc, perc));
        }
    }//procedure01


        static void procedure02(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert new employee");

            System.out.print("\t\tFirst Name: ");
            String fn = input.next();
            if (fn.equals("-1")){return; } //return to the main menu

            System.out.print("\t\tLast name: ");
            String ln = input.next();
            if (ln.equals("-1")){return; } //return to the main menu

            System.out.print("\t\tMaximum possible amount of Compensation per month: ");
            double gmc = inputValidityDouble(input.next()); //validation test
            if (gmc == -1){return; } //return to the main menu

            employees.newEmployee(new Employee(fn, ln, gmc));
        }//procedure02


        static void procedure03(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert an employee's expense\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //validation test
            if (i == -1){return; } //return to the main menu
            Employee empl = employees.getEmployee(i-1);

            System.out.print(expenseTypes);
            System.out.print("\n\t\tSelect an expense type: ");
            i = inputValidityInt(input.next(), expenseTypes.numOfExpTypes()); //validation test
            if (i == -1){return; } //return to the main menu
            ExpenseType type1 = expenseTypes.getExpenseType(i-1);

            if(type1 instanceof ExpenseType1){
                System.out.print("\t\t\tQuantity: ");
            }else{
                System.out.print("\t\t\tValue: ");
            }
            double vq = inputValidityDouble(input.next()); //validation test
            if (vq == -1){return; } //return to the main menu


            System.out.print("\t\t\tJustification: ");
            String just = input.next();
            if (just.equals("-1")){return; } //return to the main menu

            empl.newExpense(new SpecificExpense(empl, type1, vq, just)); //insert to the employee's expenses list
            allExpenses.newExpense(new SpecificExpense(empl, type1, vq, just)); //insert to the general expenses list
        }//procedure03


        static void procedure04(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert an employee's deposit\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees() ); //validation test
            if (i == -1){return; } //return to the main menu

            Employee empl = employees.getEmployee(i-1);

            System.out.print("\n\t\tAmount of deposit: ");
            double dep = inputValidityDouble(input.next()); //validation test
            if (dep == -1){return; } //return to the main menu

            empl.newStatement(new Deposit(empl, dep)); //insert to the employee's deposits list
            allStatements.newStatement(new Deposit(empl, dep)); //insert to the general deposits list
        }//procedure04


        static void procedure05(){
            System.out.println("\t>Show all eployees' expenses");
            for( int i=0; i< employees.numOfEmployees(); i++){
                System.out.println("\t\t_._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");
                System.out.println("\t\t"+employees.getEmployee(i));
                if (employees.getEmployee(i).numOfExpenses() > 0) {
                    System.out.println(employees.getEmployee(i).getEmployee_sExpenses());
                }
                else {
                    System.out.println("\t\t\tNo expenses have been made yet"); //if the expenses list is empty
                }
            }
        }//procedure05


        static void procedure06(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Compensate an employee for their expenses\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //validation test
            if (i == -1){return; } //return to the main menu

            System.out.print("\t\tAre you sure you want to continue? 1.Yes  2.No : ");
            int answer = inputValidityInt(input.next(), 2);
            if (answer == 2 || answer==-1){ return; }//return to the main menu

            Employee empl = employees.getEmployee(i-1);

            Clearance(empl); //Clearancefor the employee

            System.out.println("\t\t\tOperation successfully completed");
        }//procedure06


        static void procedure07(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Show an employee's statements\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //elegxos egkyrothtas
            if (i == -1){return; } //return to the main menu

            Employee empl = employees.getEmployee(i-1);
            if (empl.numOfStatements() > 0) {
                System.out.println("\t\t_._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");
                System.out.println("\t\t"+empl);
                System.out.println(empl.getEmployee_sStatements());
            }else {
                System.out.println("Nothing yet"); //if the Statements list is empty
            }
        }//procedure07


        static void procedure08(){
            System.out.println("\t>Compensate all employees for their expenses");

            System.out.print("\t\tAre you sure you want to continue? 1.Yes  2.No : ");
            int answer = inputValidityInt(input.next(), 2);
            if (answer == 2 || answer == -1){ return; }//return to the main menu

            for (int i=0; i<employees.numOfEmployees(); i++){
                Clearance(employees.getEmployee(i)); //Clearancefor the employee
            }
            System.out.println("\t\t\tOperation successfully completed");
        }//procedure08


        static void procedure09(){
            System.out.println("\t>Show the final compensation of all employees\n");
            double sum = 0;
            for(int i=0; i<employees.numOfEmployees(); i++){
                Employee empl = employees.getEmployee(i);
                double amount = 0;
                boolean flag = false;
                int j=0;
                while ( j<empl.numOfStatements() && !flag){
                    //SpecificStatment contains the final sum of the employee's compensation 
                    if (empl.getEmployee_sStatements().getStatement(j) instanceof SpecificStatement){
                        amount = empl.getEmployee_sStatements().getStatement(j).getAmount();
                        flag = true;
                    }
                    j++;
                }
                System.out.print("\t\t"+empl.getFirstName()+" "+empl.getLastName()+": ");
                if (flag){ //if clearance has happened
                    System.out.println("The estimated compensation is "+(Math.round(amount * 100))/100.0);
                }else{
                    System.out.println("No clearance has occurred");
                }
                sum += amount;
            }
            //print the total compensationamount (for all employees)
            System.out.println("\tThe estimated total sum of compensation is "+(Math.round(sum * 100))/100.0);
        }//procedure09


        static void Clearance(Employee empl){
			//deletion of previous statements of the employee (except for the deposits)
            for(int i=empl.numOfStatements()-1 ; i >=0; i--){
                if(!(empl.getEmployee_sStatements().getStatement(i) instanceof Deposit)){
                    empl.deleteStatement(i);
                }
            }
            //deletion from the general statements list
            Statement st;
            for(int i=allStatements.numOfStatements()-1 ; i >=0; i--){
                st = allStatements.getStatement(i);
                if (st.getEmployee() == empl && !(st instanceof Deposit)){
                    allStatements.deleteStatement(i);
                }
            }

            empl.liquidation(expenseTypes); //compensation-clearance method

			//addition of new movements (after the clearance) of the employee in the general statements list
            for (int i=0; i < empl.numOfStatements(); i++){
                st = empl.getEmployee_sStatements().getStatement(i);
                if (!(st instanceof  Deposit)){
                    allStatements.newStatement(st);
                }
            }

        }//Clearance


        static int inputValidityInt(String in, int upperBound){
            boolean ok = false;
            do {
                if (in.equals("-1")){ // -1 to return to the main menu
                    ok = true;
                    break;
                }
                for (int i = 1; i <= upperBound; i++) {
                    if (in.equals(String.valueOf(i))) {
                        ok = true;
                    }
                }
                if(!ok ){
                    System.out.print("\t\t\tInvalid Input! Please enter a number between 1 and "+upperBound+": ");
                    in = input.next();
                }
            }while(!ok);

            return Integer.parseInt(in);

        }//inputValidityInt


        static double inputValidityDouble(String in){  //we assume a number is given
            //-1 to return to the main menu
            while((Double.parseDouble(in)<0) && !(in.equals("-1"))){
                System.out.print("\t\t\tInvalid Input! Please enter a positive number: ");
                in = input.next();
            }
            return Double.parseDouble(in);

         }//inputValidityDouble


        static double percentageValidity(String in){  //we assume a number is given
            //-1 to return to the main menu
            while(Double.parseDouble(in) > 1 || (Double.parseDouble(in) < 0 && !(in.equals("-1")))){
                System.out.print("\t\t\tInvalid Input! Please enter a percentage (eg. 0.6): ");
                in = input.next();
            }
            return Double.parseDouble(in);
        }//percentageValidity

    }//mainApp




