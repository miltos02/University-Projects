public class List
{
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    private class Node 
    {
        String item;
        Node next; 
        Node(String item) 
        {
            this.item = item;
            next = null; 
        }
        public void setNext(Node next) 
        {
            this.next = next;
        }

        public String getData()
        {
            return this.item;
        }
    }

    public List() 
    {
        this.size=0;
    }

    public boolean isEmpty() 
    {
        return head == null;
    }

    public void insertAtFront(String data) 
    {
        Node n = new Node(data);
        if (isEmpty()) 
        {
            head = n;
            tail = n;
        } else {
            n.setNext(head);
            head = n;
        }
        this.size++;
    }

    public void remove(String data) 
    {
        if (head != null)
        {
            int i=0;
            Node previous=null;
            Node current = head;   
            while (current != null)
            {
                if (current.item.equals(data))
                {
                    if (i == 0)
                    {
                        if (head == tail)
                        {
                            head = tail = null; 
                        }
                        else
                        {
                            head=head.next;
                        }
                    }
                    else
                    {
                        previous.setNext(current.next);
                    }
                    break;
                }
                previous=current;
                i++;
                current = current.next;
            } 
            this.size--; 
        }       
    }

    public int getSize()
    {
        return this.size;
    }

    public boolean search(String x)
    {
        Node current = head;    
        while (current != null)
        {
            if (current.item.equalsIgnoreCase(x))
                return true;   
            current = current.next;
        }
        return false;    
    }

    public String getNext(int i)
    {
        Node current=head;
        for (int j=0; j < this.size; j++)
        {
            if (j == i)
            {
                break;
            }
            current=current.next;
        }
        return current.getData();
    }
}