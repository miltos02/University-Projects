# Assignment – Part 1 Description

The Accounting Department of a company clears every month the total of the employees’ expenses.  
We distinguish two types of expenses:  

### Expense Types
1. **Form 1 (Quantity × Rate):**  
   - Example:  
     - Travel expenses = *Number of kilometers × Reimbursement rate per kilometer*  
     - Meal expenses = *Number of days × Daily reimbursement*  

2. **Form 2 (Value × Reimbursement Percentage):**  
   - Example:  
     - Taxi fare = *Trip value × Reimbursement percentage*  

---

### Common Characteristics of Expense Types
- Code  
- Description  
- Maximum monthly reimbursement (per employee)  

**Form 1 Expenses additionally have:**  
- Reimbursement rate per unit of measurement  
- Unit of measurement (e.g., kilometers, days)  

**Form 2 Expenses additionally have:**  
- Reimbursement percentage  

---

### Employee Characteristics
- Surname  
- First name  
- Maximum monthly reimbursement amount (for all types of expenses)  

---

### Expense Characteristics
- Employee  
- Expense type  
- Value or quantity of expense (e.g., number of kilometers, taxi fare value)  
- Justification of expense (e.g., stay at ASTIR hotel, meal at ACROPOLIS restaurant)  

---

### Expense Clearance Process
1. For each expense, calculate the reimbursement value based on its type formula.  
2. Sum the reimbursement values **per employee and per expense type**.  
3. The reimbursement amount per expense type per employee is approved in full,  
   or up to the maximum monthly reimbursement of that expense type (if exceeded).  
   - This maximum check is applied only when the field value ≠ 0.  

---

### Recording Reimbursement
The new reimbursement amounts (per employee and per expense type) are recorded as **employee transactions** with the following characteristics:  
- Employee  
- Expense type  
- Reimbursement amount  

**Examples:**  
- `Papadopoulos, Travel expenses, €234.56`  
- `Anastasiou, Meals, €156.70`  

---

### Special Rules
- If the **total reimbursement of an employee exceeds their maximum monthly reimbursement**,  
  the difference is recorded in the transaction list.  
- The transaction file includes:  
  - Reimbursement transactions  
  - Difference transactions  
  - Advance payment transactions  

---

### Transaction Characteristics
- Common to all: Employee, Amount  
- Clearance transactions additionally: Expense type  

---

### Final Reimbursement Formula
Final reimbursement amount for an employee is:  

Σ(reimbursement transactions) – Σ(advance payments) – Difference amount
  
  At the end of the clearance process, the **final reimbursement amount of each employee** is recorded as a special transaction.  

---

### Cleanup Rule
Transactions from previous clearances **must be deleted before the start of a new clearance**.    

---  

## Requirements:

### A)
Create the necessary classes to describe **Employees**, **Expense Types**, **Employee Expenses**, and **Employee Transactions**. Design and implement the class hierarchy so that:

- code reuse is maximized,  
- common class attributes are placed as high as possible in the hierarchy, and  
- you exploit polymorphism when calling common methods.

---

### B)
Implement four collections that model:

1. the list of **expense types**,  
2. the list of **employees**,  
3. the list of **employee expenses**, and  
4. the list of **employee transactions**.

The expense types list should contain objects of type *ExpenseType*, the employees list objects of type *Employee*, the employee expenses list objects of type *EmployeeExpense*, and the employee transactions list objects of type *EmployeeTransaction*.  
For implementing these collections use or extend an appropriate collection from the `java.util` package.

---

### C)
Write a **Java program** which:

- initializes the list of all expense types with **at least three** expense types of form 1 and **at least three** expense types of form 2,  
- initializes the list of employees with **at least four** employees,  
- initializes the list of employee expenses with **at least four** expenses per employee, and  
- initializes the list of employee transactions with **at least one advance-payment transaction per employee**.

After initializing the lists, the program prints a menu from which the user can choose an operation by entering a number: `"1"` to insert a new expense type, `"2"` to insert a new employee, etc.

Below are the menu operations:

- **Insert new expense type:** The program asks for the expense type details.  
- **Insert new employee:** The program asks for the employee details.  
- **Insert new employee expense:** The program asks the user to select the employee, the expense type, and—depending on the expense type—the quantity or the value of the expense and the expense justification (description).  
- **Insert new advance-payment transaction:** The program asks the user to select the employee and the advance amount.  
- **Show employee expenses:** The program prints the list of employee expenses.  
- **Clear (settle) a single employee's expenses:** The program prints the list of employees; the user selects the employee whose expenses should be cleared. The program clears that employee’s expenses and updates that employee’s transactions.  
- **Show employee transactions:** The program prints the list of employees; the user selects an employee. The program prints that employee’s transactions.  
- **Clear expenses of all company employees:** The program clears the expenses of all employees.  
- **Show final monthly reimbursement amount for all employees:** The program prints, for each employee, their surname, first name and the employee’s monthly reimbursement amount. At the end it prints the total final reimbursement amount.

