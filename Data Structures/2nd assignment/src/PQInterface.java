public interface PQInterface {
        public boolean isEmpty(); //check if the queue is empty
        public int size(); //return the number of active elements in the queue
        public void insert(Processor x); // insert the object x to the queue
        public Processor max(); /*return without removing the object with
            the highest priority */
        public Processor getmax();/* remove and return the object
            with the highest priority*/
}