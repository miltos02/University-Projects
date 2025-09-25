# Theater Booking Multithreaded Application 

In this project, we implement a seat reservation system for theater performances, using the POSIX threading library (pthreads). In this system, each customer first selects the seats they want, then proceed to pay with a credit card, and finally, the funds are transferred to the theater's account. The program ensures that the customers are served fairly (the first customer to arrive will be served first), while any changes to shared variables are protected using appropriate synchronization mechanisms (mutexes). 

## **Description:**
The seat reservation system maintains the `bank account` of the theater, `two telephone operators`, and `four cashiers` who serve the `customers`. The theater has `Nseat` available seats, organized in `rows` and `columns` (the theater has a rectangular layout). Customers call to reserve their seats by row, and each row is divided into two zones: `Zone A` and `Zone B`, with different pricing policies. Zone A has a higher price, while Zone B is cheaper. The first customer calls the phone line at time zero, and every subsequent customer calls with a randomly determined interval. The system processes a total of `Ncall customers`, and all telephone operators are initially occupied, the customer will wait until an operator is available. Each customer connects to a randomly chosen telephone operator, selects the desired number of seats, and then provides a list of acceptable rows. The operator, based on the customer's preferences, randomly selects a row from the list (1-PzoneA) and then a random seat in the specified row. If the selected seats are available, they are reserved for the customer. Otherwise, the system examines whether there are adjacent available seats in another row.

The seats are filled based on customer preferences. If there are no available adjacent seats, the customer is notified about the inability to fulfill the request. In this case, the failure rate and the total time taken are recorded, and the process proceeds to payment with the credit card. After selecting the seats, the customer is transferred to a cashier and waits if all cashiers are busy. When the customer connects to a cashier, they must successfully complete the payment with the credit card. If the payment is not successful (e.g., credit card declined), the transaction is canceled, and the reserved seats are released. Otherwise, the customer receives a receipt for the payment, the total cost is calculated, and the funds are transferred to the theater's account. The process continues for all customers in the call list. When all Ncall customers have been processed, the system prints out its final results.

### Program Input
Certain constants are declaired in the [theater-booking-res](./theater-booking-res.h) file. The program only asks two parameters from the user as an input: The number of customers to be served ( `Ncust`) and the random seed for the random number generator.

### Program Output

For each customer, one of the following messages is printed on the screen, depending on the outcome of their reservation, which start with the customer's ID number:

- "The reservation was completed successfully. The seats are in zone `<a>`, row `<b>`, seat `<c>`, <...> and the cost of the transaction is `<x>` euros."
- "The reservation failed because there were no suitable available seats."
- "The reservation failed because the transaction with the credit card was not accepted."

At the end of the execution, the system prints the following:

- The theater seating map, e.g., Zone A / Row 1 / Seat 1 / Customer 3, Zone A / Row 2 / Seat 5 / Customer 4, etc.
- The total income from the sales.
- The percentage of transactions completed for each of the three messages mentioned above.
- The average waiting time of customers (from the moment a customer appears until they speak with the operator) and the transaction time (from when the customer completes the call with the operator until they complete the payment).
- The average service time of customers (from the moment a customer appears until they are served, including if a reservation is completed).

### Code Structure

At the beginning of the program, a total of `Ncust` threads are created (equal to the number of customers) and a unique ID number (from 1 to `Ncust`) is assigned to each one. Each customer thread follows the following steps: Upon creation, it sleeps for a period of time and then attempt to make its reservation. After completing their reservation or being notified that it could not be fulfilled, they print the appropriate message to the screen. Finally, the program waits until all threads have finished and print the final output.

A global array of customers is used, and each customer is assigned to a separate thread. Changes to the array are protected with appropriate mutex locks to avoid data conflicts between threads.

Additionally, condition variables and locks are used to synchronize the operations and avoid potential deadlocks. The threads have random processing times, and the system handles cases where customers wait due to unavailable resources (e.g., cashiers or operators).

Special care is taken for critical sections in the code to ensure data consistency and the fair handling of customer requests.

The code consists of two functions: the `main` function and the `customer` function, which serves as the routine executed by each thread, with each thread representing a customer. These functions are found in the file `theater-booking-res.c`, which contains the code for the seat booking system for theater performances. In this file, we include the file `theater-booking-res.h`, where variables, constants, and functions are declared.

## **More Specifically:**

## Main Function
Initially, the `main` function takes parameters for the number of customers (`Ncust`) and the seed (`seedp`). In `main`, we initialize the mutexes, create the array with the customer IDs, the two-dimensional seating array, and the thread array. Next, we create and join the threads, print the required output messages, and finally destroy (destroy) the corresponding mutexes and free the space occupied by the arrays. In total, the program has eight mutexes and two condition variables. The eight mutexes are as follows:

1. `pthread_mutex_init(&layout, NULL)`: relates to changes made to the seating array, which has global scope.
2. `pthread_mutex_init(&statistics, NULL)`: pertains to the counting array (`stats[3]`) used for calculating the statistics displayed at the end.
3. `pthread_mutex_init(&bank_account, NULL)`: concerns the calculation of the revenue that will be deposited into the bank account.
4. `pthread_mutex_init(&print_screen, NULL)`: involves locking the screen when the output is printed.
5. `pthread_mutex_init(&cashiers, NULL)`: updates the variable representing the number of available cashiers.
6. `pthread_mutex_init(&telephonists, NULL)`: updates the variable representing the number of available telephonists.
7. `pthread_mutex_init(&service, NULL)`: updates the total service time counter.
8. `pthread_mutex_init(&wait, NULL)`: updates the total waiting time counter.

The two condition variables (`cond1`, `cond2`) are used for synchronizing customers with available telephonists and cashiers.

## Customer Function
At the start of the `customer` function, after initializing local variables, the total service time counter begins immediately, which concludes at the end of the function. The customer then waits for an available telephonist, and the waiting time is updated if no telephonist is available. Using an array (`possible1[10]`), we simulate the probability of requesting seats in Zone A or B. Specifically, we fill the array according to the probabilities given in the statement, and with a random number, we determine which position in the array gives the zone the customer wants. If there are no seats available, the counter for failed bookings due to lack of seats is updated. If there are seats available as requested by the customer, we assign the customer's ID to the corresponding positions in the seating array, ensuring they remain reserved until the end of the service, regardless of whether it concludes successfully. Generally, an empty seat in the array has a value of zero, so in the case of a rejected transaction, we replace the IDs with zero, and the seats are free again. Additionally, the cost is calculated based on the number of seats and the zone they are located in, and the customer waits for an available cashier, with the waiting time updated if no cashier is available at that moment. We use another array (`possible2[10]`) which determines the probability of the transaction being successful or not, functioning similarly to the `possible1[10]` array. If the transaction is successful, the counter for successful bookings is updated; otherwise, the counter for failed bookings due to rejected transactions is updated.

![General Logic Architecture](./picture%20for%20README.png)  