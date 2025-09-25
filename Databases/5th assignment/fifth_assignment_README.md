# **Databases- Assignment - Normalization and Grouping and Aggregation Queries**

**AUEB | Spring Semester 2021-2022** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)

# Purpose
We continue with some more normalization changes and then run grouping and aggregation queries.

## Prerequisites
We assume that the database is in the state described in Assignment 4.

## Assignment Requirements

### Part A: Normalization of Tables

1. Consider the following functional dependencies, which were confirmed through queries in the previous exercise:
   - In the Movie_Cast table, the attribute `person_id` functionally determines the attributes `name` and `gender`. That is, we cannot have two actors with the same `person_id` and different `name` or `gender`.
   - In the Movie_Crew table, the attribute `person_id` functionally determines the attributes `name` and `gender`.
   
   Based on the previous functional dependencies, check if the database is in BCNF (Boyce-Codd Normal Form). If it is not, take the necessary steps to normalize it. Describe the steps of normalization, and assume that the tables resulting from the normalization of Movie_Cast will be named: Movie_Cast (the new version of the original table) and Actor. Similarly, the tables resulting from the normalization of Movie_Crew will be named: Movie_Crew (the new version of the original table) and CrewMember.

### ER Diagram

2. Assume that the entity classes (which result from the previous tables) Actor and CrewMember are subclasses of a new class Person. Consider that the class Person will contain all the common attributes of Actor and CrewMember.

3. Update the ER diagram to include the entity classes Actor and CrewMember, the superclass Person, and add the corresponding attributes and keys, as well as the associations of the new classes with the rest of the diagram.

### SQL

Using the BCNF normalization and the ER diagram:

4. Write the SQL code that will create the tables Person, Actor, and CrewMember with the corresponding primary and foreign keys.

5. Write the query that checks which records violate the functional dependencies `person_id → name` and `person_id → gender` for the table resulting from the union of Movie_Cast and Movie_Crew. This query can be done with a join between the two tables. If any record violating the functional dependencies is found, make the corresponding changes for that record using the UPDATE command.

6. Write the SQL code that will insert the appropriate records from the Movie_Cast and Movie_Crew tables into the Person, Actor, and CrewMember tables. Use INSERT with the result of a SELECT query with the appropriate fields.
   - Be careful that the INSERTs do not violate the primary key conditions for the 3 tables. Use the UNION command during the INSERT for the Person table.
   - Be careful that the order of the INSERTs does not violate the foreign key conditions between the 3 tables.

7. Make the necessary additions of foreign keys and deletions of attributes for the normalized Movie_Cast and Movie_Crew tables. Before deleting the attributes and creating new foreign keys for the two tables, create copies of the tables Movie_Cast2 and Movie_Crew2, which should not be deleted for backup reasons, as well as to allow the examiners to perform the appropriate checks related to the previous exercises.

Write the SQL code for steps 4, 5, 6, and 7 in a file named `part1.sql` along with the corresponding descriptions in comments; the BCNF normalization in a file named `BCNF.docx` or `BCNF.pdf`; and the ER diagram in the corresponding image file.

# Part B

Create 3 aggregation queries that have meaningful results.
- All should use the Person table.
- All must include at least one join.
- At least 2 of the queries must have a WHERE clause.
- At least 2 of the queries must include a GROUP BY clause.
- At least 2 of the queries must have a HAVING clause.

Write all the queries in a file named `part2.sql`. Add brief descriptions of the queries and the result counts in the same file as comments. For example:
```sql
/* "Find the titles of movies with an average rating from users greater than 4, along with their average ratings"
Output: 205 rows
*/
SELECT m.title, avg(r.rating) as avgRating
FROM movie m
INNER JOIN ratings r
ON m.id = r.movie_id
GROUP BY m.id, m.title
HAVING avg(r.rating) > 4;
```
# Deliverables:

- Create a .txt file that includes your Azure instance's endpoint (Server name on the Overview tab of Azure), the name of your database, and the username and password of a user with read-only permissions, so that we can view the tables in your database. The .txt file should have the following format:
```
Endpoint: <name_of_the_endpoint>
Username: <username>
Password: <password>
Database: <name_of_the_database>
```
- Place the files `part1.sql`, `BCNF.docx/pdf` (which here is our report below), `part2.sql`, the image file of the ER diagram, and the .txt file in a folder. The folder name should consist of your student IDs separated by dashes, for example, student_id_1-student_id_2. Create a .zip file of this folder, which should have the same name as the folder.

# Our Report:
We observe that the database is not in BCNF form. This is primarily due to the `movie_cast` and `movie_crew` tables, as the other tables in the database do not exhibit functional dependencies that are trivial. Specifically, in the `movie_cast` table, the attribute `person_id` functionally determines the attributes `name` and `gender` (i.e., it is a FD), while in the `movie_crew` table, the attribute `person_id` also functionally determines the attributes `name` and `gender` (i.e., it is also a FD).

Furthermore, these specific FDs in each table are trivial and prevent the database from being in BCNF form. Therefore, in order to normalize the database, the attributes `name` and `gender` must be removed from the tables and inserted into two new tables, with `person_id` as the primary key, named `Actor` and `CrewMember`.

Thus, the `Actor` table will contain the `gender` and `name` of the actor corresponding to the respective `person_id` in the `movie_cast` table. Similarly, the `CrewMember` table will contain the specific attributes corresponding to the `person_id` of the "worker" in the `movie_crew` table.

