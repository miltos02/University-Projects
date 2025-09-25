# **Databases- Assignment - Exploring MovieLens Data**

**AUEB | Spring Semester 2021-2022** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)

## Purpose:
In this assignment, we will explore the MovieLens data using SQL queries.

## Prerequisites:
You should have created the database described in Assignment 2 and imported the MovieLens data into the tables.

## What We Will Create:
- 12 SQL queries that will include `inner join`, `outer join`, `where`, `order by`, `group by`, `limit`, as well as the use of functions like `min`, `max`, `avg`, the keyword `distinct`, and comparison operators like `like`, `between`.
- Each table from (i) Movie, (ii) Genre, (iii) Keywords, (iv) Movie_cast, (v) Movie_Genres, (vi) Movie_Keywords, (vii) Ratings should be used in at least one query.
- Each query should be accompanied by a brief description explaining its purpose, i.e., what is being requested. It should also include the number of records returned as a result.
- At least 8 queries should include at least one `join`.
- At least 2 queries should include at least one `outer join`.

## Tools:
- Postgres Cloud SQL instance
- Postgres `psql` and/or `PgAdmin`

## Instructions:
- Place all SQL queries in a file named `simple_queries.sql`.
- Add brief descriptions of the queries and the result counts in the same file as comments. For example:
```sql
/* "Find the titles of the movies with an average user rating above 4, along with their average rating." Output: 205 rows */
 SELECT m.title, avg(r.rating) as avgRating
 FROM movie m
 INNER JOIN ratings r
 ON m.id = r.movie_id
 GROUP BY m.id, m.title
 HAVING avg(r.rating)>4
 ```

## Implementation Tips:
- If a column does not have the desired data type, change its data type using the `ALTER TABLE/ALTER COLUMN` command. For example, to convert the `rating` field in the `ratings` table to `float` (from `TEXT`), use the command:
  ```sql
  ALTER TABLE ratings ALTER COLUMN rating
  TYPE float USING (rating::float);```

- Run and test each query in your MovieLens database.

- Ensure that each query provides meaningful data exploration, i.e., it should not simply display a table. The goal is to combine criteria to extract some insight. For example, `SELECT * FROM movie;` or `SELECT * FROM movie WHERE id="123";` would not be considered appropriate.

- The answers to the queries should be understandable to a movie enthusiast. For example, a query returning "the IDs of movies with an average rating above 4" would not be correct. Instead, a correct query would return "the titles of movies with an average rating above 4."

- Use the command `\i <full_path/filename>` in `psql` to run an SQL script. For example, `\i /home/db_course/simple_queries.sql`. In `pgAdmin`, however, meta-commands (commands starting with `\`) do not work. `pgAdmin` can load and execute SQL scripts normally.

- Make sure that the database user whose credentials you provide indeed has access to your database.

## Deliverables:

1. Create a .txt file that includes the following information: the endpoint of your Azure instance (Server name on the Overview tab of Azure), the name of your database, and the username and password of a user with read-only permissions, so that we can view the tables in your database. The .txt file should have the following format:
```
Endpoint: <name_of_the_endpoint> 
Username: <username> Password: <password> 
Database: <name_of_the_database>
```
2. Place the `simple_queries.sql` file and the .txt file in a folder. The folder name should consist of your student IDs separated by dashes, for example, `student_id_1-student_id_2`. Create a .zip file of this folder, with the same name as the folder.
