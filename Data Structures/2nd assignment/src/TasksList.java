public class TasksList {
    private Node head=null;
    private Node tail=null;
    private int size=0;
    private int sum=0;
    private String str=" ";
    private class Node {
        Task item; Node next; 
        Node(Task item) {
            this.item = item;
            next = null; 
        }
    }

    public boolean isEmpty(){
        return (head == null); 
    }

    public void insertAtBack(Task item) {
        Node n = new Node(item);
        tail = n;
        if (isEmpty()) {
            head = tail;
            this.size=1;
        } else {
            n.next = tail; 
            this.size+=1;
        }
        this.str+=Integer.toString(item.getTime()+" ");
        this.sum+=item.getTime();
    }

    public Task peekFromFront(){
        Task item = head.item;
        return item;
    }

    public Task peekFromBack(){
        Task item = tail.item;
        return item;
    }

    public int getSum(){
        return this.sum;
    }

    public int size(){
        return this.size;
    }

    public String toString(){
        return this.str;
    }
}