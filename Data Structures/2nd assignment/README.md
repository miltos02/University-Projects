# **Data Structures - Assignment 2**

**AUEB | Fall Semester 2021-2022 | Instructor: E. Markakis** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)

### Sorting and Priority Queues

The purpose of the second assignment is to familiarize students with sorting algorithms and the use of priority queues, one of the most fundamental data structures in algorithm design. The assignment addresses queries that, among other things, are encountered in operating system applications and job scheduling problems.

Suppose we have a system with M processors, in which we want to schedule processes. For example, let’s say N processes have arrived, and for each i = 1, ..., N, the process i requires a processing time equal to 
𝑝, the same for all processors. We want to assign each process to a processor, with the ultimate goal of completing the execution of all processes in the shortest possible time. Each process will be executed exclusively on one processor.

### **Part A**

Before proceeding to the algorithms that you will implement, let's start with the ADTs that you will need.

**ADT Processor:** You must first implement a data type that represents a processor. Name this class Processor. An object of Processor must: 
* have a unique, positive integer id assigned when it is created (useful for debugging), 
* contain a list named `processedTasks`, which is initialized as empty, and during the execution of an algorithm will contain the processes that have already been processed by this specific processor (this list will be updated by the algorithm in Part B, and each node in the list will correspond to one process),
* have a method `getActiveTime()`, which returns the time that the processor has spent on the processes it has processed (that is, the sum of the `processing times` of the processes from the processedTasks list or 0 if it has not processed anything).

In the Processor class, you can add any other fields that you consider useful. Finally, the Processor must implement the `interface Comparable<Processor>` so that you can use it in a priority queue. For the implementation of the interface, you simply need to implement the compareTo function, where you will compare the active time of the processors. Specifically, with a call like `A.compareTo(B)`, you should determine if processor A has done more work than processor B. In case of a tie, give priority to the processor with the smaller id (see the algorithm in Part B).

**ADT Task:** You will also have a data type that represents a process. Name this class Task. An object of Task must:

* have a unique, positive integer id (useful for debugging),
* contain a field `time` that corresponds to the processing time of the task.

**ADT Priority Queue:** You will need an efficient data structure for a priority queue, where you will store objects of type Processor. The implementation of the queue will be done using a max heap, as we have seen in the course and in the lab. You can base it on the priority queue from Lab 6 or on what we covered in Section 11 of the slides, with appropriate modifications, or you can create your own queue from scratch. Your queue must implement the following interface (you can create the queue using generics, in which case you will modify the interface accordingly).
```
public interface PQInterface {  
    public boolean isEmpty(); // check if the queue is empty 
    public int size(); // return the number of active elements in the queue 
    public void insert(Processor x); // insert the object x into the queue 
    public Processor max(); /* return without removing the object with  
    the highest priority */ 
    public Processor getmax(); /* remove and return the object 
    with the highest priority */ 
}
```
The complexity that each method must have, in a queue with n objects, is given in the table below:

| Method          | Complexity              |
|-----------------|-------------------------|
| size, isEmpty, max | O(1)                  |
| insert          | O(log n) (see comments) |
| getmax          | O(log n)                |

**Comments:**

* The implementation of the priority queue should be in the file `MaxPQ.java`.
* The insert method should also call a resize method that doubles the array when the queue is 75% full. Therefore, the requirement for O(log n) in insert only applies when it does not need to call resize to expand the array.

### **Part B**

The first algorithm you are required to implement for scheduling is as follows:
#### **Algorithm 1 (Greedy)**
Process the tasks in the order they appear in the input file. For 𝑖 =1,...,𝑁, place task i on the processor that has completed the least amount of work at that moment (i.e., has spent the least amount of time processing tasks). If there are ties, the task will go to the processor with the smaller id.

**Example:** Suppose we have 3 processors P1, P2, P3 (with ids 1, 2, 3, respectively) and 5 tasks with processing times of 25, 30, 20, 35, and 50. Then the algorithm would assign the first 3 tasks to different processors (the 1st to P1, the 2nd to P2, and the 3rd to P3). The 4th task would then be assigned to P3, since at the time we decide for the 4th task, P3 has only worked for 20 minutes. Finally, the 5th task would go to P1, and the final assignment would be: P1: {25, 50}, P2: {30}, P3: {20, 35}. The quantity of interest to print in this final assignment is the time at which all tasks have been completed. This quantity is called makespan and is determined by the most "loaded" processor. In this example, we have: `Makespan = 75`

**Input and Output**  
**Input:** The client program will read from a .txt input file, first the number of processors, then the number of tasks, and then in each subsequent line the id of a task and its processing time. All data will be positive integers. For our example, the input file could have the following format (the ids below are just random numbers):  
```
3
5
12 25
1 30
32 20
128 35
4 50
```
If the number of tasks on the second line does not correspond to how many lines follow, the program should print an error message and terminate. Do not worry about what to do if the file is not in the above format. Also, assume that the task ids will be unique (the processing times do not necessarily need to be unique).

**Output:** The program should calculate and print the time at which all tasks will be completed (i.e., the makespan), based on the Greedy algorithm. Additionally, if the number of tasks is less than 50, print the processors in ascending order based on the time each has been active. For each processor, print its id, the total time it spent processing, and then the processing time of each task it processed. For example, for our example, you might print the output in the following format:
```
id 2, load=30: 30  
id 3, load=55: 20 35 
id 1, load=75: 25 50 
Makespan = 75
```
**Instructions:**  
* The program for Part B must be named `Greedy.java`. It should contain a main method where you can call methods for reading the input and executing Algorithm 1.
* The implementation of Algorithm 1 must use the priority queue `MaxPQ`. Without the queue, to find which processor to assign the next task to at each step, you would either have to scan all the processors to find out which one has done the least work or keep them sorted. By using the queue, the complexity for executing each step improves significantly.
* **Be careful with the use of MaxPQ:** the `MaxPQ` will be based on a max heap, meaning that getmax always returns the element with the highest priority. However, in Algorithm 1, we want to select the processor with the minimum active time up to that moment. Consider how you can use the `MaxPQ` to select the correct processor at each step. If you follow the example from Lab 6, you need to think about how to define the priority of a processor and construct the appropriate Comparator (using the compareTo method of the Processor class).
* For reading the input file, you can use standard Java methods for file opening as you did in Assignment 1.
* **You are not allowed to use ready-made implementations of list, stack, and queue structures from the Java library** (e.g., Vector, ArrayList, etc.). You may, of course, use code from the labs.

### **Part C:**  
In the previous example, it appears that we could improve the makespan if we assigned the most time-consuming process with a time of 50 to a processor by itself. For example, the assignment P1: {50}, P2: {35, 20}, P3: {30, 25} would yield a makespan of 55. This observation leads to the following modification of Algorithm 1:

#### **Algorithm 2 (Greedy-decreasing)**  
Sort the tasks in descending order from the largest to the smallest and then apply Algorithm 1.

You will need to process the tasks in such a way that they are in descending order. To do this, you must implement a sorting algorithm. The algorithm to be used will be either `Heapsort` or `Quicksort`. **You are not allowed to use the ready-made sorting methods provided by Java.** You must implement your own method, either using an array or using a list.

* If your student ID ends with an even number, you should implement `Heapsort`.
* If it ends with an odd number, you should implement `Quicksort`.
* If you are in a group and both student IDs end with even numbers or both with odd numbers, follow the above rules.
* Otherwise, choose either of the two methods you prefer. 
The only deliverable for Part C is the class Sort.java with the sorting algorithm (there is no need to have a main method in there).

### **Part D:**  
In this part, you will conduct a small experimental evaluation to determine which of the two algorithms performs better in practice concerning the makespan. Use the Random class from the java.util package to generate random input data (i.e., create random processing times for the tasks). Use at least 3 different values for the number of tasks.

As an example, you can use 𝑁=100,250,500. For each value of 
𝑁, create 10 different input .txt files for algorithms 1 and 2. In total, you should create at least 30 files of random test data. For each 𝑁, use 𝑀=⌊√𝑁⌋ processors.

Then, write a program that will execute and compare the two algorithms. For each input file, record the makespan for each algorithm. After that, calculate the average makespan for each algorithm for the 10 test files corresponding to that particular value of 𝑁. Comment on the results you observe from this evaluation.

**Additional Implementation Instructions:**  
* The program for Part D must be named Comparisons.java. The main method of this program will read the input, run Algorithm 1, and then to execute Algorithm 2, it will use the sorting from Part C and re-run Algorithm 1 with sorted input. Your program will either run both algorithms in a loop for all the test input files you create or perform one execution of the algorithms for one input file. If you choose the second option, you must run it as many times as the input files you have created and record the makespan found each time.
* For better code development, it is convenient to separate your programs into modules according to their functionality. For example (this is not mandatory),
* In Part B, you can write a separate method that reads the input file and stores the tasks in an array or list.
* You can implement Algorithm 1 to take as input the array or list you have read, so you do not have to deal further with the input .txt file.
* In this way, the implementation of Algorithm 2 requires only sorting the array/list, and you can reuse the previous pieces of your code from Part B.  
---
# Our Report:

### **Part A:**

We created 4 classes named `Processor`, `MaxPQ`, `TasksList`, and `Task`. In the file `Processor.java`, we have a constructor for `Processor` objects, where each one has its own `TasksList`, from which the time spent by the processor on the tasks it has processed is derived using the `getActiveTime` method. The `compareTo` method of a `Processor` takes another `Processor` object as an argument and returns the difference between the total times of these two processors. Additionally, there are various getter methods that are useful in different parts of the assignment where we need access to the attributes of `Processor` objects.

In the file `Task.java`, we have a constructor for `Task` objects and two getter methods: one that returns the unique ID of each `Task`, and another that returns the processing time of each task. Then, in the file `TasksList.java`, a list of tasks is created (the list of tasks for each processor), and the inner class `Node` creates the nodes that contain the various `Tasks`. The `isEmpty` method returns a boolean value depending on whether the list is empty, and the `insertAtBack` method adds a `Task` to the `TasksList` at the back. The `peekFromBack` method always returns the task at the back of the list, while `peekFromFront` returns the one at the front.

In the file `MaxPQ.java`, we create the priority queue in which the processor with the smallest `activeTime` always has the highest priority. The constructor for `MaxPQ` creates the queue, which uses an array of `Processor` objects as a max heap. The `comparison` method takes two `Processor` objects as arguments and returns the one with the highest priority, using the `compareTo` method. The `insert` method adds a `Processor` to the queue, the size of which adjusts depending on how full it is via the `resize` method, while the `removeItem` method removes an item and updates the queue size. Additionally, the `max` method returns the processor that has the highest priority at that moment. In contrast, the `getmax` method returns and removes that element, updating the size of the queue. The `swim` and `sink` methods adjust the elements in the queue to maintain the correct priority. Finally, the `swap` method swaps the positions of the elements passed as arguments.

### Greedy.java:

The file `Greedy.java` implements Algorithm 1, which is executed if the user selects the number 1. Initially, a `MaxPQ` object is created along with a `BufferedReader` object that reads a `.txt` file (specifying how many processors are needed, how many operations will be performed, and then each operation individually). It updates the `MaxPQ` with the processors and the task list of each processor based on the file’s contents. At the end of the Greedy algorithm (after checking that the file is not problematic), we start removing the processor with the highest priority using `getmax` in a loop, and the total time it processed the tasks is added to a variable `makespan`, which was initialized to 0. Additionally, if there are fewer than 50 tasks, for each processor returned by `getmax`, we display its ID, active time, and the list of task times that the specific processor executed, using the various getter methods mentioned earlier.

### **Part C:**

Regarding Part C of the assignment, we used the Quicksort sorting algorithm that we wrote in the file `Sort.java`, because our student IDs end in an odd number. The algorithm is implemented using an array containing `Task` objects. In `Greedy.java`, we create an object of type `Sort`, which takes as an argument an array of `Tasks` that has a size corresponding to the number of operations. Each operation is added to the `Task` array, which is then sorted using the `Quicksort` method before the data is displayed.

### Part D:

Regarding Part D, we call the algorithm from Parts 1 and 2, passing as an argument that we are in Part D, so that the results are not displayed to the user but are instead stored for comparison and to show the effectiveness of the two algorithms in relation to their performance on the makespan. Using the values 100, 225, and 625 that we set for \(N\) (the number of tasks), we create 10 `.txt` files for each value of \(N\), resulting in the following table, which presents the average makespan for each value:

| N   | Greedy | Greedy Decreasing |
|-----|--------|-------------------|
| 100 |  2658  | 2484              |
| 225 |  5857  | 5214              |
| 625 | 17589  | 16893             |

From the table above, we can conclude that in every case, the Greedy Decreasing algorithm produces a smaller makespan than the Greedy algorithm. This means that the average makespan is also smaller than that of the Greedy algorithm, leading us to conclude that Greedy Decreasing is more efficient, as it more accurately prioritizes the tasks, allowing them to be completed in less time.


