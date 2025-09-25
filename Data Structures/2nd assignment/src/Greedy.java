import java.io.*;
import java.util.StringTokenizer;
import java.util.*; 

public class Greedy {
    public static void main(String[]args){
        boolean partC=false;
        boolean partD=false;
        Scanner sc= new Scanner(System.in); 
        System.out.println("Greedy or Greedy Decreasing? Press 1 for Greedy or 2 for Greedy Decreasing");
        int answer= sc.nextInt();
        if (answer==2) partC=true;
        sc.close();
        String nameofFile=args[0];
        int makespan=greedyFunction(partC, partD, nameofFile);
        System.out.println("Makespan = " + makespan);
    }
    static int greedyFunction(boolean partC, boolean partD, String nameofFile){
        PQInterface pq= new MaxPQ(); //creation of Priority queue
        int makespan=0; //declares when all processes have been completed
        try{
            BufferedReader buff = new BufferedReader(new FileReader(nameofFile));
            StringTokenizer Tokens;
            String token;
            String line;
            Processor pr; 
            int processors=0; 
            int operations=0; 
            int lineCounter=1; 
            int operationsDone=0; 
            int processorsDone=0; 
            int a=1; //counter for tokens
            int id=0; 
            int time=0; 
            boolean wrongFile=false; 
            boolean hasFifty=true; //is the number of processors less than 50?
            Task[] taskArray= new Task[0]; //created in case Greedy Decreasing is selected
            Sort st=new Sort(taskArray);
            line = buff.readLine();
            while(line!=null){
                Tokens = new StringTokenizer(line);
                while(Tokens.hasMoreTokens()){
                    token=Tokens.nextToken();
                    for(int i=0;i<token.length();i++){ 
                        //checking the tokens. only digits and spaces allowed          
			            if(!Character.isDigit(token.charAt(i)) && !(token.charAt(i)==' ')){
				            wrongFile=true;  
                            break;  
                        }      	   
		            }
                    if(wrongFile) break;
                    //updating  processors and operations variables
                    if(lineCounter==1){
                        processors=Integer.parseInt(token);
                    }else if(lineCounter==2){
                        operations=Integer.parseInt(token);
                        if(partC) taskArray=st.resizeTask(operations);
                    }
                }
                if(lineCounter>2) {
                    if (operationsDone == operations) { //check the operations limit
                        wrongFile = true;
                        break;
                    }
                    String[] arr = line.split(" ");
                    for (String w : arr) {
                        if (a == 1) {
                            id = Integer.parseInt(w);
                        } else {
                            time = Integer.parseInt(w);
                        }
                        a++;
                    }
                    Task task = new Task(id, time);
                    if (!partC) {
                        if (operationsDone < processors) { //save processors and their first operations
                            pr = new Processor(operationsDone + 1);
                            pr.getProcessedTasksList().insertAtBack(task);
                            pq.insert(pr);
                            operationsDone++;
                        } else { //save tasks according to their priority
                            pq.max().getProcessedTasksList().insertAtBack(task);
                            operationsDone++;
                        }
                    } else {
                        taskArray[operationsDone] = task; //add task in taskArray
                        operationsDone++;
                    }
                }
                line = buff.readLine();
                lineCounter++;
                if(wrongFile) line = null;
            }
            buff.close();
            if(!wrongFile){
                if(partC){
                    if(operationsDone>=1){
                        taskArray = st.quicksort(taskArray,0,operationsDone-1);
                    }
                    operationsDone=0;
                    while(operationsDone<operations){
                        if (processorsDone<processors){
                            pr=new Processor(operationsDone+1);
                            pr.getProcessedTasksList().insertAtBack(taskArray[operationsDone]);
                            pq.insert(pr);
                            processorsDone++;
                        }else{
                            if(partD && operationsDone==operations-2) break; 
                            pq.max().getProcessedTasksList().insertAtBack(taskArray[operationsDone]);
                            processorsDone++;
                        }
                        operationsDone++;
                    }
                }
                if(operations<50 || !partD) hasFifty=false;
                a=0;
                while(!pq.isEmpty()){
                    a++;
                    pr=pq.getmax();
                    if(!hasFifty){
                        System.out.println("id "+ pr.getId()+ ", load="+pr.getActiveTime()+":"+ pr.getProcessedTasksList().toString());
                    }
                    if(a==processors){
                        makespan=pr.getActiveTime();
                    }
                }
            }else{
                System.out.println("Problematic .txt file");
            }
        }catch (IOException ex) {
            System.err.println("Error at reading .txt file");
        }
        return makespan;
    }
}