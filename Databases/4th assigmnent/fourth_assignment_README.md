# **Databases- Assignment - Redisigning our database**

**AUEB | Spring Semester 2021-2022** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)

## Purpose:
We are making changes to the design of our database in order to normalize it in the next step and avoid anomalies. In this context, we need to:

- Clean our data of noise and unnecessary information.
- Represent the schema of the database in an Entity-Relationship (ER) diagram.
- Extend our original schema to include primary keys for all tables.

## Prerequisites:
You should have created the database described in Assignment 2 and imported the movie data into the tables.

## Assignment Requirements:
In this assignment, we are making changes to our database starting from the state it was in as defined in Assignment 2. Our goal is to create primary keys for all tables and the corresponding Entity-Relationship diagram. The steps of the assignment are as follows:

### Cleaning the Tables
1. Duplicate records should be deleted from the tables `Movie_Cast`, `Movie_Collection`, `Movie_Crew`, `Movie_Genres`, `Movie_Keywords`, and `Movie_ProductionCompanies`. To simplify the deletion process, use the following command, placing the table name in the `TableName` field:
```sql
DELETE FROM TableName
 WHERE ctid IN(
 SELECT t1.ctid
 FROM TableName t1
 NATURAL JOIN TableName t2
 WHERE t1.ctid>t2.ctid)
```
2. Using the UPDATE command on the `Movie_Cast` table, change the values of the Character attribute from NULL to 'UNKNOWN'. Why is this specific change necessary regarding Step 6?

### Table Constraints
Write SQL queries that confirm the following desired properties of our database:

3. In the `Movie_Cast table`, the person_id attribute functionally determines the name and gender attributes. That is, we cannot have two actors with the same person_id and different name or gender.  
  **Idea:** Create a query that returns all person_ids that violate this condition. If the specific query has an empty response, then the property we are looking for holds true.  
  **Helpful:** The solution should use the GROUP BY and HAVING commands along with the aggregation function `COUNT(DISTINCT ATTRIBUTE)`. The `DISTINCT` parameter is used so that repeated elements of the `ATTRIBUTE` are not counted more than once.

4. In the `Movie_Crew` table, the person_id attribute functionally determines the name and gender attributes.

### Creation of ER Diagram
5. Create the entity-relationship diagram for the movielens database. In the entity-relationship diagram, the corresponding key attributes should be underlined, while for tables with a large number of attributes, some of the remaining attributes should be drawn indicatively. For the entity-relationship diagram, also consider the user entity that describes users who rated movies.

### Creation of Keys
6. Using the ALTER TABLE command, create primary keys for the following tables in the database: `Movie_Collection, Movie_Crew, Movie_Cast, Movie_Genres, Movie_ProductionCompanies, Movie_Keywords`. If the aforementioned keys were created in the second assignment, provide the corresponding `CREATE` command that was used to create them.

### Tools:
- Postgres Cloud SQL instance
- Postgres psql and/or PgAdmin
- Draw.io

# Deliverables

**A.** Create a .txt file that includes your Azure instance's endpoint (Server name on the Overview tab of Azure), the name of your database, and the username and password of a user with read-only permissions, so that we can view the tables in your database. The .txt file should have the following format:
```
Endpoint: <name_of_the_endpoint>
Username: <username>
Password: <password>
Database: <name_of_the_database>
```

**B.** Create the file `functional_dependencies.sql` which contains and explains the functionality of the queries created in steps 3 and 4 of the exercise.

**C.** Create the ER diagram of the database as described in step 5 of the exercise and submit it in PDF format.

**D.** For each table from `Movie_Collection`, `Movie_Crew`, `Movie_Cast`, `Movie_Genres`, `Movie_ProductionCompanies`, `Movie_Keywords`, create the corresponding SQL file (e.g., `movie_cast.sql`) that contains the commands that modified the corresponding table by adding primary keys in step 6 of the exercise. Along with the SQL code, provide a description of the changes.

- Place the SQL files, the image file, and the .txt file in a folder. The folder name should consist of your student IDs separated by dashes, i.e., `student_id_1-student_id_2`. Create a .zip file of this folder, which should have the same name as the folder.

