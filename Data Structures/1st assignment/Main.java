public class Main {
    static StringStackImpl sStack = new StringStackImpl();
    static IntQueueImpl intQueue = new IntQueueImpl();

    public static void main(String[] args) {
        sStack.push("Gwgw");
        sStack.push("Christos");
        sStack.push("Alviona");
        sStack.push("Xaris");
        System.out.println(sStack.size());
        sStack.pop();
        System.out.println(sStack.size());
        sStack.printStack(System.out);
        System.out.println(sStack.peek());
        sStack.pop();
        sStack.pop();
        sStack.printStack(System.out);
        //sStack.peek();
        sStack.pop();
        sStack.pop();   
    }
}