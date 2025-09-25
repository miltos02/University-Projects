# **Data Structures - Assignment 3**

**AUEB | Fall Semester 2021-2022 | Instructor: E. Markakis** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)

#### **Binary Search Trees**  
The purpose of Assignment 3 is to familiarize students with data structures used for implementing a symbol table, such as binary search trees. The task for this assignment is to create a Word Counter. The main function of the word counter is to "load" a file of English text and count how many times each word appears. For example, let's look at the following text:
```
Hello, how are you?  
Very well, thank you.   
I study for the exams.   
How about you?
Fine, thank you.  
How many exams will you have?  
Too many…  
```
The word "you" appears 5 times, the word "how" 3 times, the words "thank," "many," and "exams" appear 2 times each, while the words "hello," "are," "very," "well," "I," "study," "about," "fine," "will," and "have" appear once each. The Word Counter should ignore punctuation marks such as , . ? ; ! - :, as well as parentheses, brackets, mathematical symbols, or any other character that is not a letter of the English alphabet. For example, when the text reads "well," it should remove the comma, leaving only the word "well." If there is a phrase in parentheses, such as "(Maria asked me)," it should consider only the words Maria, asked, and me.

Only single apostrophes within words are allowed, e.g., "don't" should be considered as one word. Additionally, all strings containing numbers, such as "17:25" or "1980’s," should be completely ignored. Finally, the user of the program should be able to specify special words (stop words) that they want to be fully ignored, such as articles like "a," "an," and "the" in relevant applications. The program should be case-insensitive. For example, the words "Hello" and "hello" are considered the same.  

### **Part A**

To get started, you need to define the following two classes.

**The WordFreq class:** For each different word you read, you should create an object with this word as the key. Specifically, each word corresponds to an object of type `WordFreq`. This class contains (at least) two fields: the word itself (private String) and the number of occurrences (private int). You can overload the toString method appropriately here to help with printing results. The `WordFreq` class should also contain a key() method that returns the key, i.e., the word.  

**The TreeNode class:** This class will be private within the symbol table (see Part B), and objects of this class correspond to the nodes of the binary search tree that you will use. Each node in the tree is an object of `TreeNode` type and should contain an object of type WordFreq as described above. Additionally, it should include pointers to the left and right subtrees, and a field indicating the number of nodes in the subtree starting from this node. Therefore, the `TreeNode` class should contain at least the following fields (and possibly any other fields you want to add):
```
private class TreeNode {
    WordFreq item;      // The word frequency item
    TreeNode left;      // Pointer to the left subtree
    TreeNode right;     // Pointer to the right subtree
    int subtreeSize;    // Number of nodes in the subtree starting at this node
    ...
}
```
Access to the key of the node should be done via the `key()` method of the `item`. If `h` is an object of type `TreeNode`, the key of the node can be obtained by calling `h.item.key()`, as shown in the lecture slides.


### **Part B**

**The class for the symbol table/BST:** The structure for implementing the symbol table will be a Binary Search Tree (BST). The class you will define will be called BST.java and will follow the outline below:
```
public class BST implements WordCounter {
    private class TreeNode {
        ...
    };
    private TreeNode head; // Root of the tree
    private List stopWords; // List of stopwords
    ...
}
```
The `WordCounter` interface contains the following methods (with explanations following):
```
public interface WordCounter {
    void insert(String w);
    WordFreq search(String w);
    void remove(String w);
    void load(String filename);
    int getTotalWords();
    int getDistinctWords();
    int getFrequency(String w);
    WordFreq getMaximumFrequency();
    double getMeanFrequency(); 
    void addStopWord(String w);  
    void removeStopWord(String w);  
    void printTreeAlphabetically(PrintStream stream); 
    void printΤreeByFrequency(PrintStream stream); 
}
```
#### **Brief Description of the Required Methods:**  
* `void insert(String w):` Searches to see if a node with key `w` already exists in the tree. If it does, it increases its frequency by 1. If not, it inserts a new node into the tree (using a simple leaf insertion) with this key and a frequency of 1.

* `WordFreq search(String w):` Searches the tree for the existence of the word `w` (returns `null` if not found). The `search` method will initially work like the corresponding method we covered in the lectures, with one important difference: when it finds the word `w` in the tree, if the frequency of `w` is greater than the mean frequency (from `getMeanFrequency()`), it brings the word to the root of the tree using rotations. One way to do this (but not the only way) is to first call remove to `remove` this node from the BST and then perform an insertion at the root for that specific node. The idea is that words with high frequency are likely to be searched for often, so we want to keep them as high as possible in the tree.

* `void remove(String w):` Removes the node with key `w` (if such a node exists). You can use the removal method discussed in the lectures.

* `void load(String filename):` Starting from the current BST (which could be empty), reads the file named `filename`, containing English text, and updates the tree with the words read to create the final BST. The requirements mentioned on Page 1 regarding punctuation, the presence of numbers, ignoring words specified as stop words, etc., should be followed. There is no need to check if the text is indeed in English, or if the words are valid (e.g., if the word "matematics" appears instead of the correct "mathematics," it will be treated as a regular new word).

* `int getTotalWords():` Returns the total number of words in the text that has been loaded into the BST, taking into account the frequency of each word (ignoring already-excluded stop words, numbers, etc.). This can be done with a simple tree traversal (using any traversal method you prefer).

* `int getDistinctWords():` Returns the number of distinct words in the tree. It should run in `O(1)`.

* `int getFrequency(String w):` Returns the number of occurrences of the word w (if the word is not in the tree, it returns 0).

* `WordFreq getMaximumFrequency():` Returns a `WordFreq` object containing the word with the most occurrences (sorting is not required to solve this problem). In the case of a tie, you can arbitrarily choose which word to return.

* `double getMeanFrequency():` Calculates and returns the mean frequency. The mean is derived from the frequencies of all distinct words in the text.

* `void addStopWord(String w):` Adds the word w to the stop words list. It should run in `O(1)`.

* `void removeStopWord(String w):` Removes the word w from the stop words list.

* `printTreeAlphabetically(PrintStream stream):` Prints the words in the tree along with the number of occurrences of each word, in alphabetical order. It should be implemented using some tree traversal method.

* `printTreeByFrequency(PrintStream stream):` Prints the words and their number of occurrences, sorted in ascending order based on the number of occurrences.  
#### **Comments and Additional Implementation Guidelines:**  
* You can rely on the code from the lecture slides and lab sessions for the BST implementation.
* Use Java's standard library methods for converting uppercase to lowercase, removing punctuation, and any other processing you perform on the words. As in Assignments 1 and 2, there are various methods you can use to read and process text.
* Do not consider any stop word as given. The list of stop words will be created through calls to `addStopWord`. For the stop words list, implement any type of list you want (singly or doubly linked), without using Java’s built-in implementations.
* The key for the objects stored in the tree is of type `String`. Therefore, comparing keys here means comparing strings. Also, each key will appear in at most one node of the tree.
* Several methods require tree traversal. Therefore, you should implement one or more traversal methods within the symbol table.
* Make sure to update the `subtreeSize` field in the `TreeNode` class correctly.
* The implementation of `printTreeByFrequency` can be done either by combining a tree traversal with a subsequent sorting method (using Quicksort, Mergesort, or Heapsort), or in other ways. Alternatively, for example, you can (although it's not necessary, as the first method is simpler) create a temporary BST on the spot with an appropriate key type and a suitable comparator (consider what needs to be compared) to traverse the tree based on the frequency of each word.
* **Method complexity:** There is no strict requirement to achieve the optimal complexity for all methods, except for those explicitly stated in the explanations on the previous page. You can use what we have discussed in the course as a guideline. However, if you implement something extremely time-consuming, e.g., if you implement `printAlphabetically` in `O(N²)` time, then you will not receive all the points allocated for that method. In general, you should avoid `O(N²)` complexity for all methods except for `load`.
* You are not allowed to use built-in data structure implementations for lists, queues, trees, etc., from Java's library (e.g., Vector, ArrayList, etc.).

# Our Report:

### **Part B**

- **`void insert(String w)`**: This method inserts a node into the tree if it does not already exist in the tree; otherwise, it updates the field that tracks the number of occurrences of a word. Its operation is recursive, and with each node addition, the `subTreeSize` field is updated accordingly.

- **`WordFreq search(String w)`**: This method searches for a word in the tree and returns the corresponding `WordFreq` element if it exists, or `NULL` otherwise. Additionally, if the frequency of the word being searched for is greater than the mean frequency (from `getMeanFrequency()`), it will bring that word to the root of the tree using rotations (methods `rotateRight` and `rotateLeft`) through the `insertHead` method. After changes to the tree structure, the `subTreeSize` fields are updated accordingly.

- **`void remove(String w)`**: This method removes a node from the tree if it is not empty, after first finding it with a recursive search. After the node is removed, its "parent" is replaced by the next node, if there is one. Once the node is removed, the `subTreeSize` fields are updated accordingly.

- **`void load(String filename)`**: This method loads the `.txt` file into the BST. The words from the `.txt` file are checked according to the requirements stated in the instructions (regarding how punctuation should be handled, etc.), and if the word is not included in the list of `stopWords`, it is inserted into the tree. Before each load, we check if a word has been added to `stopWords` that was previously loaded into the tree, and if so, we remove it. We assume that there is a space after each period in the `.txt` file.

- **`int getTotalWords()`**: This method returns the total number of words in the `.txt` file. It recursively sums the occurrence frequencies of each word, i.e., each node in the tree.

- **`int getDistinctWords()`**: This method returns the number of distinct words in the `.txt` file. It returns a counter variable (`numItems`) for the tree nodes, which is updated with each `insert` and `remove` operation (runs in O(1) time).

- **`int getFrequency(String w)`**: This method returns the number of occurrences of the given word in the `.txt` file. If the word is not found, it returns `NULL`.

- **`WordFreq getMaximumFrequency()`**: This method returns the word that appears most frequently in the `.txt` file. The word is found through recursive comparisons of the elements. If the tree is empty, it returns `NULL`.

- **`double getMeanFrequency()`**: This method calculates the mean frequency, which is the average of the frequencies of all distinct words. This is obtained by dividing the total number of words (using the recursive method from `getTotalWords`) by the number of distinct words (`numItems` counter variable).

- **`void addStopWord(String w)`**: This method adds the given word to the `stopWords` list using the `insertAtFront` method defined in the `List.java` class (runs in O(1) time).

- **`void removeStopWord(String w)`**: This method removes the given word from the `stopWords` list using the `remove` method defined in the `List.java` class.

- **`printTreeAlphabetically(PrintStream stream)`**: This method prints the words in the tree in alphabetical order, along with how many times each appears. The method works recursively and, for each node, it calls the `toString` method defined in the `WordFreq.java` class to display the information as desired.
- **`printTreeByFrequency(PrintStream stream)`**: This method prints the words in the tree in ascending order based on their frequency. The ascending sorting is implemented using Quicksort, which is achieved by a tree traversal method that temporarily maps the tree elements to an array.
