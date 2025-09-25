public class Task {
   int id ;
   int time;
   Task(int id, int time){
       this.id=id;
       this.time=time;
   } 
   
   public int getId(){
       return this.id;
   }
   
   public int getTime(){
       return this.time;
   }
}