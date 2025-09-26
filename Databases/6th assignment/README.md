# **Databases- Assignment - Final Project**

**AUEB | Spring Semester 2021-2022** 

## Τhe instructions for the assignment

###### (the instructions have been translated from greek to english and only the parts that we considered relevant were kept in this .md file)
# Purpose

In this project, we will analyze and visualize the data from the Movielens database (https://movielens.org), which we examined in previous assignments. This database contains information about movies, their contributors, and user ratings for the films.

# Groups

To complete the project, you should form groups of 2 or 3 individuals, no more and no less.

# Data

You can load the exercise data using the Backup found at the following link or by using the database you created in previous exercises. 

For those who create the database from the link, the restoration of the database is done in the following steps:
- Select the database in which the new tables will be created.
- Right-click and select restore.
- In the format field "Custom or tar", enter the filename of the downloaded file.

# Visualization of Statistics

In this section, you should calculate and visualize the following statistics using SQL and Python (via connection to your database).

## Movie Statistics

1. Number of movies per year.
2. Number of movies by genre.
3. Number of movies by genre and by year.
4. The highest movie budget per year (we are not interested in which movie).

## Actor Statistics

5. For your favorite actor, the total revenue for the movies in which they participated per year.

## User Ratings

6. Average rating per user (scatter plot).
7. Number of ratings per user (scatter plot).
8. Scatter plot with one point for each user where the x-axis shows the number of ratings for the user and the y-axis shows their average rating.
9. Average rating by movie genre.

**Note:** Since our database does not contain the full list of movies, we consider that some of the statistics may not be accurate.

# Deliverables

You need to submit the following:

1. An SQL file that contains the commands you used to calculate all of the above.
2. The Python file that connects to the database (with examiner credentials) and visualizes the data.
3. A PDF containing the visualizations along with a brief explanation of the information being depicted. If you believe that there is an alternative way to visualize any of the questions, you can add it along with the corresponding explanation.


# Our Report:

## QUESTION 1
In this chart, the number of movies (NUMBER OF MOVIES) is depicted by year (RELEASE YEAR). We have considered that NULL values do not need to be displayed in the chart because some movies did not have a release year.  
![General Logic Architecture](./img/question%201.png)  

## QUESTION 2
In this specific chart, the number of movies (NUMBER OF MOVIES) is illustrated by genre (GENRES). As observed, the largest number of movies belongs to the Drama category.  
![General Logic Architecture](./img/question%202.png)  

## QUESTION 3
This chart shows the number of movies by genre (GENRES) and by year (YEARS/MOVIES). We have considered that NULL values do not need to be displayed in the chart because some movies did not have a release year. Additionally, we observe a significant discrepancy in the number of movies according to the year and according to the genre.  
![General Logic Architecture](./img/question%203.png)  

## QUESTION 4
In this chart, the highest movie budget is depicted by year (YEARS). We have considered that NULL values do not need to be displayed in the chart because some movies did not have a release year.  
![General Logic Architecture](./img/question%204.png)  

## QUESTION 5
In this chart, the total revenue (REVENUE) for the movies in which the actor Tom Cruise has participated is illustrated by year (YEARS).  
![General Logic Architecture](./img/question%205.png)  

## QUESTION 6
In this scatter plot, the average rating (AVERAGE RATING) is depicted by user (USER_ID).  
![General Logic Architecture](./img/question%206.png)

## QUESTION 7
In this scatter plot, the number of ratings (RATINGS) is shown by user (USER_ID).  
![General Logic Architecture](./img/question%207.png)

## QUESTION 8
A scatter plot is depicted which has one point for each user, where the x-axis shows the number of ratings for the user (NUMBER OF RATINGS) and the y-axis shows the average rating (AVERAGE RATING).  
![General Logic Architecture](./img/question%208.png)

## QUESTION 9
In this chart, the average overall rating (AVERAGE RATING) is illustrated by genre (GENRES).  
![General Logic Architecture](./img/question%209.png)


