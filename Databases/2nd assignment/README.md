# **Databases- Assignment - MovieLens Tables**

**AUEB | Spring Semester 2021-2022** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)
## CSV Data Representation

### What is a CSV file?

CSV stands for "Comma-Separated Values." It is the simplest form of storing data in a tabular format. 

The typical structure of a CSV file is that the first line contains the header, while each line contains a record, where the values of the record are separated by commas. For example, suppose we have the file `students.csv` with the following content:

```
id, name, surname, age
el22053, Γιάννης, Παπαδόπουλος, 22
el22057, Κώστας, Ιωάννου, 25
```

The above file corresponds to the table:

| id       | name    | surname       | age |
|----------|---------|---------------|-----|
| el22053  | Γιάννης | Παπαδόπουλος  | 22  |
| el22057  | Κώστας  | Ιωάννου       | 25  |

### Reading a CSV file in Python involves the following steps:

1. Import the CSV library.
```Python
import csv
```
2. Open the CSV file, which returns a file object.
```Python
file = open('students.csv')
```
3. Read the CSV file using the csv.reader object.
```Python
csvreader = csv.reader(file)
```
4. Extract the header fields using the next method, which will return the next element from the csvreader (in this case, the first row).
```Python
header = next(csvreader)
```
5. Extract and print the remaining records. Each row is structured as an array where, for example, the first value (row[0]) will return the student's ID.
```Python
for row in csvreader:
    print(row)
```
6. Close the file using the .close() method to ensure that no further operations can be performed on it.
```Python
file.close()
```
**Complete Code**
```Python
import csv
file = open("Salary_Data.csv")
csvreader = csv.reader(file)
header = next(csvreader)
print(header)
for row in csvreader:
    print(row)
file.close()
```

###  To write data to a CSV file:

1. Import the CSV library.
```Python
import csv
```
2. Open the CSV file for writing (using mode 'w').
```Python
f = open("Salary_Data.csv", 'w')
```
3. Create a CSV writer object.
```Python
writer = csv.writer(f, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
```
4. Write data to the CSV file using writerow() or writerows() methods.
```Python
writer.writerow(row)
```
5. Close the file after finishing writing data to it.
```Python
f.close()
```
**Complete Code**
```Python
import csv
f = open("Salary_Data.csv", 'w')
writer = csv.writer(f)
row = ['a', 'b', 34, 57]
writer.writerow(row)
f.close()
```
### Reading from JSON/AST Strings involves these steps:

1. To process a JSON string, use the ast library of Python (it is useful for reading strings in Python's abstract syntax grammar, which has a structure similar to JSON).
```Python
import ast
```
2. Suppose the string contains information about keywords in a movie.
```Python
jsonString = "[{'id': 931, 'name': 'jealousy'}, {'id': 6054, 'name': 'friendship'}]"
```
3. Using the literal_eval method, we can get the contents of the jsonString in the appropriate object format for further processing. For example, the command `data = ast.literal_eval(jsonString)` will return a list where each element is a dictionary that refers to a keyword object. For example,
```Python
print(data)  # prints [{'id': 931, 'name': 'jealousy'}, {'id': 6054, 'name': 'friendship'}]
print(data[0])  # prints {'id': 931, 'name': 'jealousy'}
print(data[0]['id'])  # prints 931
```
## Deliverables:

- Create a .txt file that includes the following information:
  - Full names and student IDs of the team members, the endpoint of your Azure instance, the name of your database, and the username and password of the examiner user or another user with read-only permissions, so that we can view the tables in your database. The .txt file should have the following format:
    ```
    <Full Name 1> - <Student ID 1>
    <Full Name 2> - <Student ID 2>
    Endpoint: <name_of_the_endpoint>
    Username: <username>
    Password: <password>
    Database: <name_of_the_database>
    ```

- Place all .sql files and the .txt file in a folder. The folder name should consist of your student IDs separated by dashes, for example, `student_id_1-student_id_2`. Create a .zip file of this folder, with the same name as the folder.


# Our Report:

Initially, we chose to write the code in Python to create the two new .csv files derived from the keywords.csv file, which contained nested information. To handle the issue of alternating between blank and correct lines, we set the lineterminator parameter to `"\n"` in the function that writes the file. Additionally, we ensured that all duplicates in the keywords_set.csv file (one of the two files we created) were removed through the code.

Next, from the command line, we created tables (SQL) and set the primary key to the ID for the following entities: Movie, Genre, Collection, Keyword, and ProductionCompany. After completing this, we imported the data into pgAdmin, effectively loading the tables.

Then, for the remaining files that did not represent entities, we realized that we needed to set foreign keys. To achieve this, we created the alter_tables.sql file (which we also loaded into pgAdmin), where we used the alter table command to set the corresponding keys that relate to other tables.