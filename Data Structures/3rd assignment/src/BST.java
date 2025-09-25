import java.io.*;
import java.util.StringTokenizer;
import java.io.PrintStream;

//We considered that in load in txt after each dot theres nothing. 
public class BST implements WordCounter
{
    private class TreeNode //private class that defines the properties of the nodes
    {
        WordFreq item; //WordFreq of node
        TreeNode left; 
        TreeNode right; 
        TreeNode parent; 
        int subtreeSize=0; //number of nodes in subtree starting at this node
    
        TreeNode(WordFreq wf)
        {
            this.item=wf;
            subtreeSize++;
        }
        public void updateSubTree() //updates SubTree size
        {
            this.subtreeSize=count(this);
        }
        public int getSubTreeSize() //returns SubTree
        {
            this.subtreeSize=count(this);
            return this.subtreeSize;
        }
        public TreeNode getLeft() 
        {
            return left;
        }
        public void setLeft(TreeNode left) 
        {
            this.left = left;
        }
        public TreeNode getRight() 
        {
            return right;
        }
        public void setRight(TreeNode right) 
        {
            this.right = right;
        }
        public TreeNode getParent() 
        {
            return parent;
        }
        public void setParent(TreeNode parent) 
        {
            this.parent = parent;
        }
        public void zeroFrequency()
        {
            this.item.setWordFrequency(0); 
        }
    }
    private TreeNode head; //root of the tree
    private List stopWords=new List(); // list of stopwords
    private int loadTimes = 0;
    private int numItems = 0;

    private TreeNode rotateLeft(TreeNode pivot) 
    {
        TreeNode parent = pivot.getParent();
        TreeNode child = pivot.getRight();

        if (parent == null) 
        {
            head = child;
        } 
        else if (parent.getLeft() == pivot) 
        {
            parent.setLeft(child);
        } 
        else 
        {
            parent.setRight(child);
        }

        child.setParent(pivot.getParent());
        pivot.setParent(child);
        pivot.setRight(child.getLeft());

        if (child.getLeft() != null) 
        {
        	child.getLeft().setParent(pivot);
        }

        child.setLeft(pivot);
        updateSubTreeSizes(head); 
        return child;
    }
    
    private TreeNode rotateRight(TreeNode pivot) 
    {
        TreeNode parent = pivot.getParent();
        TreeNode child = pivot.getLeft();
        
        if (parent == null) 
        {
            head = child;
        } 
        else if (parent.getLeft() == pivot) 
        {
            parent.setLeft(child);
        } 
        else 
        {
            parent.setRight(child);
        }
        
        child.setParent(pivot.getParent());
        pivot.setParent(child);
        pivot.setLeft(child.getRight());

        if (child.getRight() != null) 
        {
        	child.getRight().setParent(pivot);
        }

        child.setRight(pivot);
        updateSubTreeSizes(head);
        return child;
    }

    public WordFreq search(String w) //searching if word w is in the tree
    {
        TreeNode current = head;
        WordFreq newHead=null;
        while (true) 
        {
            if (current == null)
                return null;
            if (current.item.key().equals(w))
            {   
                if (current.item.getWordFrequency() > getMeanFrequency()) //if current has a frequency greater than the average it becomes the head
                {
                    newHead=current.item;
                    remove(current.item.key());
                    insertHead(head,newHead);
                }
                updateSubTreeSizes(head);
                return current.item;
            }
            if (current.item.key().compareTo(w) < 0)
                current = current.getRight();
            else
                current = current.getLeft();
        }
    }

    public void insert(String w) //inserts node in the tree
    {
        WordFreq wf=new WordFreq(w);
        if (head == null)
        {
            head = new TreeNode(wf);
            numItems++;
            return;
        }
        TreeNode current = head;
        
        while (true) 
        {
            if (current.item.key().equals(w))
            {
                current.item.updateFrequency();
                updateSubTreeSizes(head); 
                return;
            }
            else
            {
                if (current.item.key().compareTo(wf.key()) < 0) //current.item.key<wf.key
                {
                    if (current.getRight() == null) 
                    {
                        current.setRight(new TreeNode (wf));
                        current.updateSubTree();
                        updateSubTreeSizes(head); 
                        numItems++;
                        return;
                    } 
                    else 
                    {
                        current = current.getRight(); 
                    }
                } 
                else 
                {
                    if (current.getLeft() == null) 
                    {
                        current.setLeft(new TreeNode (wf));
                        current.updateSubTree(); 
                        updateSubTreeSizes(head); 
                        numItems++;
                        return;
                    } 
                    else 
                    {
                        current = current.getLeft();
                    }
                }
            }
        }
    }

    private TreeNode insertHead(TreeNode h, WordFreq wf) 
    { 
        if (h == null) return new TreeNode(wf); 
        if (wf.less(h.item)) 
        { 
            h.left = insertHead(h.left, wf);
            h = rotateRight(h); 
        }
        else 
        { 
            h.right = insertHead(h.right, wf); 
            h = rotateLeft(h); 
        } 
        updateSubTreeSizes(head);
        return h;
    } 

    public void remove(String w) 
    {
        TreeNode current = head;
        TreeNode parent = null;

        while (true) 
        {
            if (current == null)
                return;

            if (current.item.key().equals(w))
            {
                break;
            }
            parent = current;
            if (current.item.key().compareTo(w) < 0)
            {
                current = current.getRight();
                current.updateSubTree();
            }
            else
            {
                current = current.getLeft();
                current.updateSubTree();
            }
        }

        TreeNode replace = null;
        if (current.getLeft() == null)
            replace = current.getRight();
        else if (current.getRight() == null)
            replace = current.getLeft();    
        else 
        {
            TreeNode findCurrent = current.getRight();

            while (true) 
            {
                if (findCurrent.getLeft() != null)
                    findCurrent = findCurrent.getLeft();
                else
                    break;
            }
            remove(findCurrent.item.key());
            findCurrent.setLeft(current.getLeft());
            findCurrent.setRight(current.getRight());
            replace = findCurrent;
            numItems--;
        }

        if (parent == null) 
        { //root
            head = replace;
        } else {
            if (parent.getLeft() == current)
                parent.setLeft(replace);

            if (parent.getRight() == current)
                parent.setRight(replace);
        }
        updateSubTreeSizes(head); 
    }

    public void load(String filename) //loads file
    {
        loadTimes++;
        if (head != null)
        {
            for (int i=0; i < stopWords.getSize(); i++)
            {
                if (this.search(stopWords.getNext(i)) != null)
                {
                    this.remove(stopWords.getNext(i));
                }
            }
        }
        if (loadTimes > 1)
        {
            clearFrequencies(head);
        }
        try
        {
            BufferedReader buff = new BufferedReader(new FileReader(filename));
            StringTokenizer Tokens;
            String token;
            String newToken=null;
            String line;
            char c; //last character
            int length; 
            boolean insertPossible;
            line = buff.readLine();
            while(line!=null)
            {
                Tokens = new StringTokenizer(line);
                while(Tokens.hasMoreTokens())
                {
                    newToken=null;
                    insertPossible=false;
                    token=Tokens.nextToken();
                    token=token.toLowerCase();
                    if (!token.equals("-") && !token.equals(".") && !token.equals(",") && !token.equals("?") && !token.equals("!") && !token.equals(":") && !token.equals(";") && !token.equals("...") && !token.equals("(") && !token.equals(")"))
                    {
                        if (!token.contains("0") && !token.contains("1") && !token.contains("2") && !token.contains("3") && !token.contains("4") && !token.contains("5") && !token.contains("6") && !token.contains("7") && !token.contains("8") && !token.contains("9")) {
                            if (token.endsWith("-") || token.endsWith(".") || token.endsWith(",") || token.endsWith("?") ||  token.endsWith("!") ||  token.endsWith(":") || token.endsWith(";") ||  token.endsWith(")") || token.endsWith("(")  || token.endsWith("#") || token.endsWith("...") || token.startsWith("(")) 
                            {
                                while (token.contains("-") || token.contains(".") || token.contains(",") || token.contains("?") || token.contains("!") || token.contains(":") || token.contains(";") || token.contains(")") || token.contains("(") || token.contains("#") || token.contains("...")) 
                                {
                                    if (token.endsWith("-") || token.endsWith(".") || token.endsWith(",") || token.endsWith("?") || token.endsWith("!") || token.endsWith(":") || token.endsWith(";") || token.endsWith("...")) 
                                    {
                                        length = token.length();
                                        c = token.charAt(length - 1);
                                        token = token.replace(c, ' ');
                                        token = token.trim();
                                    } else if (token.startsWith("(") || token.endsWith(")")) 
                                    {
                                        if (token.startsWith("(")) {
                                            c = token.charAt(0);
                                            token = token.replace(c, ' ');
                                            token = token.trim();
                                        }
                                        if (token.endsWith(")")) 
                                        {
                                            length = token.length();
                                            c = token.charAt(length - 1);
                                            token = token.replace(c, ' ');
                                            token = token.trim();
                                        }
                                    } 
                                }
                                if (!token.contains("-") && !token.contains(".") && !token.contains(",") && !token.contains("?") && !token.contains("!") && !token.contains(":") && !token.contains(";") && !token.contains(")") && !token.contains("(") && !token.contains("#") && !token.contains("...")) 
                                {
                                    newToken = token;
                                    insertPossible=true;
                                }
                            } if (!token.contains("-") && !token.contains(".") && !token.contains(",") && !token.contains("?") && !token.contains("!") && !token.contains(":") && !token.contains(";") && !token.contains(")") && !token.contains("(") && !token.contains("#") && !token.contains("...")) 
                            {
                                newToken = token.trim();
                                insertPossible=true;
                            }
                        }
                    }
                    if (insertPossible && stopWords.search(newToken))
                    {
                        insertPossible=false;
                    }
                    if (insertPossible)
                    {
                        this.insert(newToken);
                    }
                }
                line = buff.readLine();
            }
            buff.close();
        }
        catch (IOException ex) 
        {
            System.err.println("Error at reading .txt file");
        }
    }

    public void addStopWord(String w) //add word in stopWords list
    {
        stopWords.insertAtFront(w);
    }

    public void removeStopWord(String w) //remove word form stopWords list
    {
        stopWords.remove(w);
    }

    public int getFrequency(String w) //frequency of word w
    {
        if (search(w) == null)
        {
            return 0;
        }
        else
        {
            return search(w).getWordFrequency();
        }
    }

    public double getMeanFrequency() //average frequency
    {
        return (double)countAmount(head) / numItems; //subtreesize(head) +1
    }

    public WordFreq getMaximumFrequency() //most common word
    {
        if (head != null)
        {
            WordFreq max = head.item;
            if (head.getSubTreeSize() == 1)
            {
                return max;
            }
            else 
            {
                max = findMax(head, max);
            }
            return max;
        }
        return null;
    }

    public WordFreq findMax(TreeNode h,WordFreq m) //recursive search of max
    {
        if (h == null)
        {
            return m;
        }
        else
        {
            m = findMax(h.left, m);
            if (h.item.getWordFrequency() > m.getWordFrequency())
            {
                m = h.item;
            }
            m = findMax(h.right, m);
            return m;
        }
    }

    public int getTotalWords() //total words
    {
        return countAmount(head);
    }

    public int getDistinctWords() //number of different words
    {
        return numItems;
    }

    public void printTreeAlphabetically(PrintStream stream) //alphabetical print in increasing order
    {
       stream.println(toStringAlphabetically(head));
    }

    public String toStringAlphabetically(TreeNode head)
    {   
        if (head == null)
        {
            return "";
        }
        else 
        {
            String s = toStringAlphabetically(head.left) + "\n";
            s += head.item.toString() + "\n";
            s += toStringAlphabetically(head.right);
            return s;
        }
    }

    public void printTreeByFrequency(PrintStream stream) //print in increasing order according frequency
    {
        if (head != null)
        {
            WordFreq [] WordFreqArray = this.BSTtoArray(); 
            int size = count(head);
            WordFreqArray = quicksort(WordFreqArray, 0, size-1);
            for (int i=0; i < size; i++)
            {
                stream.println(WordFreqArray[i]);
            }
        }
        else
        {
            stream.println("Empty Tree");
        }
    }

    public WordFreq [] BSTtoArray() //tree in array for easier use of quicksort
    {
        int size = count(head);
        WordFreq [] WordFreqArray = new WordFreq [size];
        makeArray(head, 0, WordFreqArray);
        return WordFreqArray;
    }
    
    public int makeArray(TreeNode node, int index, WordFreq [] results) 
    {
        if (node.getLeft() != null) 
        {
            index = makeArray(node.getLeft(), index, results);
        }
        
        if (node.getRight() != null) 
        {
            index =makeArray(node.getRight(), index, results);
        }
        results[index] = node.item;
        return index + 1;
    }

    public  WordFreq[] quicksort(WordFreq[] a, int low, int high) //sorting in increasing order
    {
        if (low<high)
        {
            int pi = partition(a, low, high);
            quicksort(a, low, pi - 1); 
            quicksort(a, pi + 1, high); 
        }
        return a;
    }

    public int partition(WordFreq[] a, int low, int high) 
    {
        int pivot = high;
        int i = low - 1 ; 
        for (int j = low; j <= high-1; j++){
            if (a[j].getWordFrequency() < a[pivot].getWordFrequency())
            {
                i++;  
                swap(a,i,j);
            }
        }
        swap(a,i+1, high);
        return i+1;
    }

    public void swap(WordFreq[] a,int i, int j) 
    {
        WordFreq tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public void updateSubTreeSizes(TreeNode h) //recursive update of the subTreeSizes
    {
        if (h == null)
        {
            return ;
        }
        else
        {
            updateSubTreeSizes(h.left);
            h.updateSubTree();
            updateSubTreeSizes(h.right);
            return ;
        }
    }

    public int countSubTree(TreeNode head) 
    {
        if (head == null) return 0;
        return 1 + countSubTree(head.left) + countSubTree(head.right); 
    }
    public int count(TreeNode head) { return countSubTree(head); }

    public int countSubTreeFrequency(TreeNode head) 
    {
        if (head == null) return 0;
        return head.item.getWordFrequency() + countSubTreeFrequency(head.left) + countSubTreeFrequency(head.right);
    }
    public int countAmount(TreeNode head) { return countSubTreeFrequency(head); }

    public void clearFrequencies(TreeNode h) //recursive update of frequencies
    {
        if (h == null)
        {
            return ;
        }
        else
        {
            clearFrequencies(h.left);
            h.zeroFrequency();
            clearFrequencies(h.right);
            return ;
        }
    }
}