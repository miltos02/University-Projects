public class Sort {
    private Task[] a;
    Sort(Task[] a){
        this.a=a;
    }
    
    public Task[] quicksort(Task[] a, int low, int high) {
        if (low<high){
            /* pi is partitioning index, arr[pi] is now
            at right place */
            int pi = partition(a, low, high);
            //if(low>0 /*&& high<=a.length-1 && pi<a.length-1*/) {
                quicksort(a, low, pi - 1);  // Before pi
                quicksort(a, pi + 1, high); // After pi
            //}
        }
        return a;
    }


    public int partition(Task[] a, int low, int high) {
        int pivot = high;
 
        int i = low - 1 ; // Index of smaller element and indicates the
                       // right position of pivot found so far
    
        for (int j = low; j <= high-1; j++){
            // If current element is smaller than the pivot
            if (a[j].getTime() > a[pivot].getTime())
            {
                i++;    // increment index of smaller element
                swap(a,i,j);
                //break;
            }
        }
        swap(a,i+1, high);
        return i+1;
    }

    public Task[] resizeTask(int anInt){
        Task[] newTask = new Task[0];
        for (int i = 0; i <= a.length-1; i++) {
            newTask[i] = a[i];
        }
        return newTask;
    }

    static boolean less(int v, int w) { 
        return v < w; 
    } 
    

    public void swap(Task[] a,int i, int j) {
        Task tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}