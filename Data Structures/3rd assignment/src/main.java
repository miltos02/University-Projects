public class main 
{
    public static void main(String[]args)
    {
        BST binaryTree = new BST();
        binaryTree.addStopWord("about");
        binaryTree.addStopWord("hello");
        binaryTree.addStopWord("you");
        binaryTree.load("filename.txt");
        binaryTree.printTreeAlphabetically(System.out);
        binaryTree.removeStopWord("you");
        binaryTree.load("filename.txt");
        System.out.println("------------------------------------------------------------");
        binaryTree.printTreeAlphabetically(System.out);
        binaryTree.addStopWord("how");
        binaryTree.load("filename.txt");
        System.out.println("------------------------------------------------------------");
        binaryTree.printTreeAlphabetically(System.out);
    }
}