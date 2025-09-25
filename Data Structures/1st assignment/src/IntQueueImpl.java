import java.io.PrintStream;
import java.util.NoSuchElementException;

public class IntQueueImpl implements IntQueue{
    private Node head=null;
    private Node tail=null;
    private int size=0;
    private class Node {
    int item; Node next; 
    Node(int item) {
        this.item = item;
        next = null; }
    }
    
    public boolean isEmpty(){
        return (head == null); 
    }

    public void put(int item){
        Node t = tail;
        tail = new Node(item);
        if (isEmpty()) head = tail;
        else t.next = tail; 
        this.size+=1;
    }

    public int get() throws NoSuchElementException{
        if (isEmpty())
            throw new NoSuchElementException();
        int v = head.item;
        Node t = head.next;
        head = t;
        this.size-=1;
        return v;
    }

    public int peek() throws NoSuchElementException{
        if (isEmpty())
            throw new NoSuchElementException();
        int v = head.item;
        return v;
    }

    public void printQueue(PrintStream stream){
        if (isEmpty()){
            stream.println("The queue is empty");
        }else{
            Node iter=head;
            for (int i=size; i>0; i--){
                int v=iter.item;
                if(i!=1){
                    stream.print(v+",  ");
                    iter=iter.next;  
                }else{
                    stream.print(v+"\n"); 
                }
            }
        }
    }

    public int size(){
        return this.size;
    }
}