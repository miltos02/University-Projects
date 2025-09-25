# Assignment – Part 2 Description

In the second part of the assignment, you will **extend the program** you created in Part 1.

---

## Requirements

### A) Reading from Files

The program must read data from **four structured text files**:

1. **Expense Types List** → from the **first file**  
2. **Employees List** → from the **second file**  
3. **Employee Expenses List** → from the **third file**  
4. **Employee Transactions List** → from the **fourth file**

- The structure (format) of these files is defined in **Appendices 1, 2, 3, and 4**.  
- Reading must occur **at program startup**.  
- During reading:
  - Check whether each file opened correctly.  
  - Validate the syntax of the data.  
  - Reserved words (tags) must be recognized **case-insensitively** (uppercase or lowercase).  

---

### B) Writing to Files

The program must also **write back data** for:

- The **Employee Expenses List**  
- The **Employee Transactions List**

Both files must follow the **same structure** as defined in **Appendices 3 and 4**.

- The file containing **employee expenses** must be updated with all changes (new expenses) that occur during execution.  
- The file containing **employee transactions** must be updated with all changes (new advances, new clearance transactions) that occur during execution.  
- Writing must be performed:
  - When the program terminates, **or**  
  - When the user explicitly requests it during program execution.  

---

## Initial Setup

You may create the following files manually using a text editor:

- `expenses.txt` → list of expense types  
- `employees.txt` → list of employees  
- `employee_expenses.txt` → list of employee expenses  
- `employee_transactions.txt` → list of employee transactions  

Each file must respect the structure described in the appendices.  

---

## Notes

- The program must handle **empty lines** gracefully.  
- Invalid or unrecognized attributes must be **ignored**.  
- Mandatory fields must always exist, otherwise the corresponding entry will be skipped and a **warning message** will be displayed.  
- The order of tags inside each entity is **not fixed**.  

---
# Appendix 1 – Structure of the Expense Types File

### General Rules
- Spaces can be of **any length** and may consist of either `" "` (space) characters or tab characters (`"\t"`).  
- All entities **start and end** with `{` and `}`, each on a separate line.  
- Empty lines must be **ignored**.  

### Entities
- An **`EXPENSE_TYPE_LIST`** contains one or more **`EXPENSE_TYPE`** entities.  
- Each `EXPENSE_TYPE` must include:
  - `TYPE` → type of the expense  
  - `CODE` → code of the expense type  
  - `DESCR` → description of the expense type  
  - Any **specific characteristics** of that expense type (with custom reserved words/tags).  

### Validation Rules
- Invalid/unrecognized attributes must be **ignored**.  
- The **order of tags is not fixed**. Attributes can appear in **any order**.  
- If a tag is missing:
  - The corresponding attribute should take on a **default value**.  
  - If `CODE`, `DESCR`, or `TYPE` are missing → the entire expense type must be **ignored**, and a **warning** should be displayed.  
- For object creation:
  - The `TYPE` tag must be read before creating an instance.  
  - However, `TYPE` may not necessarily be the **first tag** encountered.  

### Robustness
During grading, the file may be modified to test your program’s **tolerance to errors in file structure**.  

---

## Example – Expense Types File

```txt
EXPENSE_TYPE_LIST
{
 EXPENSE_TYPE
 {
 TYPE 1
 CODE 1001
 DECSR “ODOIPORIKA”
 ...
 }
 EXPENSE_TYPE
 {
 TYPE 1
 CODE 1002
 DECSR “DIATROFH”
 ...
 }
 EXPENSE_TYPE
 {
 TYPE 2
 CODE 2001
 DECSR “TAXI”
 ...
 }
 ...
}
```
# Appendix 2 – Structure of the Employees File

## General Rules
- Spaces can be of **any length** and may consist of either `" "` (space) characters or tab characters (`"\t"`).  
- All entities **start and end** with `{` and `}`, each on a separate line.  
- Empty lines must be **ignored**.  

## Entities
- An **`EMPLOYEE_LIST`** contains one or more **`EMPLOYEE`** entities.  
- Each `EMPLOYEE` must include:
  - `CODE` → employee code  
  - `SURNAME` → employee surname  
  - `FIRSTNAME` → employee first name  
  - Any **specific characteristics** of the employee (using custom reserved words/tags).  

## Validation Rules
- Invalid/unrecognized attributes must be **ignored**.  
- The **order of tags is not fixed**. Attributes can appear in **any order**.  
- If a tag is missing:
  - The corresponding attribute should take on a **default value**.  
  - If `CODE`, `SURNAME`, or `FIRSTNAME` are missing → the employee must be **ignored**, and a **warning** should be displayed.  

## Robustness
During grading, the file may be modified to test your program’s **tolerance to errors in file structure**.  

---

## Example – Employees File

```txt
EMPLOYEE_LIST
{
 EMPLOYEE
 {
 CODE 1001
 SURNAME “GEORGIOU”
 FIRSTNAME “ATHANASIOS”
 MAX_MONTHLY_VAL 300
 ...
 }
 EMPLOYEE
 {
 CODE 1002
 SURNAME “PAPADOPOULOU”
 FIRSTNAME “MARIA”
 MAX_MONTHLY_VAL 200
 ...
 }
 ...
}
```
# Appendix 3 – Structure of the Employee Expenses File

## General Rules
- Spaces can be of **any length** and may consist of either `" "` (space) characters or tab characters (`"\t"`).  
- All entities **start and end** with `{` and `}`, each on a separate line.  
- Empty lines must be **ignored**.  

## Entities
- An **`EXPENSE_LIST`** contains one or more **`EXPENSE`** entities.  
- Each `EXPENSE` must include:
  - `EXPENSE_TYPE` → type of the expense  
  - `EXPENSE_CODE` → code of the expense type  
  - `EMPLOYEE_CODE` → employee code  
  - `VAL` → value of the expense  

## Validation Rules
- Invalid/unrecognized attributes must be **ignored**.  
- The **order of tags is not fixed**. Attributes can appear in **any order**.  
- If a tag is missing:
  - The corresponding attribute should take on a **default value**.  
  - If `EMPLOYEE_CODE`, `EXPENSE_TYPE`, `EXPENSE_CODE`, or `VAL` are missing → the expense must be **ignored**, and a **warning** should be displayed.  

## Robustness
During grading, the file may be modified to test your program’s **tolerance to errors in file structure**.  

---

## Example – Employee Expenses File

```txt
EXPENSE_LIST
{
 EXPENSE
 {
 EMPLOYEE_CODE 1002
 EXPENSE_TYPE 1
 EXPENSE_CODE 1001
 VAL 30
 ...
 }
 EXPENSE
 {
 EMPLOYEE_CODE 1002
 EXPENSE_TYPE 1
 EXPENSE_CODE 1002
 VAL 20
 ...
 }
 EXPENSE
 {
 EMPLOYEE_CODE 1002
 EXPENSE_TYPE 2
 EXPENSE_CODE 2001
 VAL 50
 ...
 }
 ...
```

# Appendix 4 – Structure of the Employee Transactions File

## General Rules
- Spaces can be of **any length** and may consist of either `" "` (space) characters or tab characters (`"\t"`).  
- All entities **start and end** with `{` and `}`, each on a separate line.  
- Empty lines must be **ignored**.  

## Entities
- A **`TRN_LIST`** contains one or more **`TRN`** entities.  
- Each `TRN` must include:
  - `EMPLOYEE_CODE` → employee code  
  - `TYPE` → transaction type  
  - `VAL` → transaction value  

- For transactions of type **`APOZIMIOSI`** (compensation), the following are **also required**:
  - `EXPENSE_TYPE` → type of the related expense  
  - `EXPENSE_CODE` → code of the related expense  

## Validation Rules
- Invalid/unrecognized attributes must be **ignored**.  
- The **order of tags is not fixed**. Attributes can appear in **any order**.  
- If a tag is missing:
  - The corresponding attribute should take on a **default value**.  
  - For transactions of type `APOZIMIOSI`, if the expense type or code is missing → the transaction must be **ignored**, and a **warning** should be displayed.  

## Robustness
During grading, the file may be modified to test your program’s **tolerance to errors in file structure**.  

---

## Example – Employee Transactions File

```txt
TRN_LIST
{
 TRN
 {
 EMPLOYEE_CODE 1002
 TYPE “PROKATAVOLI”
 VAL 150
 ...
 }
 TRN
 {
 EMPLOYEE_CODE 1002
 TYPE “APOZIMIOSI”
 EXPENSE_TYPE 1
 EXPENSE_CODE 1001
 VAL 75
 ...
 }
 TRN
 {
 EMPLOYEE_CODE 1002
 TYPE “DIAFORA”
 VAL 25
 ...
 }
 TRN
 {
 EMPLOYEE_CODE 1002
 TYPE “FINAL_APOZIMIOSI”
 VAL 50
 ...
 }
 ...
}
```