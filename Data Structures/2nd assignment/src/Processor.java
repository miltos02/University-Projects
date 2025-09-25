public class Processor{
    TasksList processedTasks;
    int id;
    Processor(int id) {
        this.id=id;
        this.processedTasks = new TasksList();
    }
   
    public int getActiveTime(){
        int sum=0;
        if (!this.getProcessedTasksList().isEmpty()){
            sum=this.getProcessedTasksList().getSum();
        }
        return sum;
    }
    
    public TasksList getProcessedTasksList(){
        return this.processedTasks;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int compareTo(Processor b){
        return this.getActiveTime()-b.getActiveTime();
    }
}