import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack{
    private Node head=null;
    private int size=0;
    private class Node {
    String item; Node next;
    Node(String item, Node next) {
        this.item = item; this.next = next; } }

    public boolean isEmpty() { return (head == null); }

    public void push(String item) { 
        head = new Node(item, head);
        this.size+=1;}

    public String pop() throws NoSuchElementException{
        if (isEmpty())
            throw new NoSuchElementException();
        String v = head.item; Node t = head.next;
        head = t; 
        this.size-=1;
        return v; } 

    public String peek() throws NoSuchElementException{
        if (isEmpty())
            throw new NoSuchElementException();
        String v=head.item;
        return v;
    }

    public void printStack(PrintStream stream){
       if (isEmpty()){
           stream.println("The stack is empty");
       }else{
           Node iter=head;
           for (int i=size; i>0; i--){ 
               String v=iter.item;
               stream.println(v);
               iter=iter.next;
            }
        }
    }

    public int size(){
        return this.size;
    }
}