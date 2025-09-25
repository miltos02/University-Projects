public class MaxPQ implements PQInterface {
    private Processor[] heap; // the heap to store data in
    private int size; // current size of the queue
    //private Comparator comparator; // the comparator to use between the objects
    private static final int DEFAULT_CAPACITY = 4; // default capacity

    MaxPQ() {
        this.heap = new Processor[DEFAULT_CAPACITY + 1];
        this.size = 0;
        //this.comparator = comparator;
    }

    public int comparison(Processor a, Processor b){
        int i=0; //returns 0 if b is of higher priority than a/greater id
        if(a.compareTo(b)<0){
            //a is of higher priority than b aka less active time
            i=1;
        }else if(a.compareTo(b)==0){
            if(a.getId()<b.getId()){
                //a has a greater id than b
               i=1;
            }
        }
        return i;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }else{
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void insert(Processor x) {
        if (size >= heap.length*0.75)
            resize();
        heap[++size] = x;
        swim(size);
    }

    public void removeItem(int id){
        heap[id] = heap[size];
        size--;
        sink(1);
    }

    private void resize() {
        Processor[] newHeap = new Processor[heap.length*2];
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    private void swim(int i) {
        if (i == 1)
            return;
        int parent = i / 2;
        while (i != 1 && comparison(heap[i],heap[parent]) > 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
    }

    private void swap(int i, int j) {
        Processor tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    public Processor max(){
        if (size == 0)
            return null;
        swim(size);
        Processor root = heap[1];
        return root;
    }

    public Processor getmax() {
        if (size == 0)
            return null;
        sink(1);
        Processor root = heap[1];
        heap[1] = heap[size];
        size--;
        return root;
    }
    
    private void sink(int i) {
        int left = 2 * i;
        int right = left + 1;
        if (left > size)
            return;
        while (left <= size) {
            int max = left;
            if (right <= size) {
                if (comparison(heap[left], heap[right]) < 0)
                    max = right;
            }
            if (comparison(heap[i], heap[max]) > 0)
                return;
            else {
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }
}