import java.util.Random;
import java.io.*; 
import java.lang.Math;

public class Comparisons{
    public static void main(String[]args){
        Random rand = new Random();
        Greedy gr=new Greedy();
        int limit=0; //n is the number of operations
        int processors=0; 
        String id;
        String time;
        String fileName; //name of .txt file
        String[] names= new String[30]; //array with the different fileNames
        String index="";
        int[] makespansGreedy=new int[30]; //array of makespans given by greedy algorithm
        int[] makespansGreedyDecreasing=new int[30]; //array of makespans given by greedy decreasing
        int[] averageMakespans= new int[6]; //array of average makespans
        for(int i=0; i<30; i++){
            index= Integer.toString(i+1);
            //grouping of the txt files according to the operations
            if(i<10){
                names[i]="n1Number"+ index+ ".txt";
            }else if(i<20){
                names[i]="n2Number"+ index+ ".txt";
            }else{
                names[i]="n3Number"+ index+ ".txt";
            }
        }
        try{
            for(int i=0; i<30; i++){
                //according to the file the number of processors is saved in limit variable
                if(i<10){
                    limit=100;
                }else if(i<20){
                    limit=225;
                }else{
                    limit=625;
                }
                fileName=names[i];
                FileWriter fw = new FileWriter(fileName);
                BufferedWriter buff = new BufferedWriter(fw);
                for(int j=1; j<=limit; j++){
                    if(j==1){ //number of processorswritten in the first line
                        processors=(int) Math.sqrt(limit);
                        buff.write(Integer.toString(processors));
                    }else if(j==2){ //number of operators written in second line 
                        buff.write(Integer.toString(limit));
                    }else{ //writes every individual operation
                        id= Integer.toString(j);
                        time= Integer.toString(rand.nextInt(60));
                        buff.write(id +" " + time);
                    }
                    if(j<limit) buff.newLine();
                }
                buff.close();
                makespansGreedy[i]=gr.greedyFunction(false, true, fileName); 
                makespansGreedyDecreasing[i]=gr.greedyFunction(true, true, fileName);
            }
            for(int i=0; i<30; i++){
                if(i<10){
                    averageMakespans[0]+=makespansGreedy[i];
                    averageMakespans[3]+=makespansGreedyDecreasing[i];
                }else if(i<20){
                    averageMakespans[1]+=makespansGreedy[i];
                    averageMakespans[4]+=makespansGreedyDecreasing[i];
                }else{
                    averageMakespans[2]+=makespansGreedy[i];
                    averageMakespans[5]+=makespansGreedyDecreasing[i];
                }
            }
            for(int i=0; i<6; i++){
                averageMakespans[i] = averageMakespans[i]/10;
            }
            System.out.println("-------------------------------------Comparing The Algorithms-------------------------------------");
            System.out.println("∼For 10 processors and 100 random operations the average makespan is: ");
            System.out.println(averageMakespans[0] +" with Greedy algorithm and "+ averageMakespans[3]+ " with Greedy-Decreasing algorithm");
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("∼For 15 processors and 225 random operations the average makespan is: ");
            System.out.println(averageMakespans[1] +" with Greedy algorithm and "+ averageMakespans[4]+ " with Greedy-Decreasing algorithm");
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("∼For 25 processors and 625 random operations the average makespan is: ");
            System.out.println(averageMakespans[2] +" with Greedy algorithm and "+ averageMakespans[5]+ " with Greedy-Decreasing algorithm");
            System.out.println("--------------------------------------------------------------------------------------------------");
        }
        catch (IOException ex) {
            System.err.println("Error at writing .txt file");
        }
    }
}