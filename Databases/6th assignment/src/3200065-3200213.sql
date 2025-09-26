/*------------------------ Movie Statistics ------------------------*/

/* 1. Number of movies per year */
SELECT EXTRACT(YEAR FROM m.release_date) as releaseYear,COUNT(EXTRACT(YEAR FROM m.release_date)) as num_Movies
FROM movie m
WHERE EXTRACT(YEAR FROM m.release_date) IS NOT NULL
GROUP BY EXTRACT(YEAR FROM m.release_date)
ORDER BY EXTRACT(YEAR FROM m.release_date) ASC;

/* 2. Number of movies by genre */
SELECT g.name as genre,COUNT(mg.genre_id) as num_Movies 
FROM genre g
INNER JOIN movie_genres mg
ON g.id=mg.genre_id
GROUP BY g.name;

/* 3. Number of movies by genre and by year */
SELECT g.name as genre,EXTRACT(YEAR FROM m.release_date) as releaseYear,COUNT(*) as num_Movies
FROM genre g
INNER JOIN movie_genres mg
ON mg.genre_id=g.id
INNER JOIN movie m
ON mg.movie_id=m.id
WHERE EXTRACT(YEAR FROM m.release_date) IS NOT NULL
GROUP BY g.name, EXTRACT(YEAR FROM m.release_date)
ORDER BY g.name;

/* 4. The highest movie budget per year (we are not interested in which movie) */
SELECT EXTRACT(YEAR FROM m.release_date) as releaseYear,MAX(m.budget) as maxBudget
FROM movie m
WHERE EXTRACT(YEAR FROM m.release_date) IS NOT NULL
GROUP BY EXTRACT(YEAR FROM m.release_date)
ORDER BY EXTRACT(YEAR FROM m.release_date) ASC;

/*------------------------ Actor Statistics ------------------------*/

/* 5. For your favorite actor (Tom Cruise), 
the total revenue for the movies in which they participated per year. */
SELECT EXTRACT(YEAR FROM m.release_date) as releaseYear,SUM(m.budget) as budget
FROM movie m
INNER JOIN movie_cast mc 
ON m.id=mc.movie_id
WHERE mc.person_id=500
GROUP BY EXTRACT(YEAR FROM m.release_date)
ORDER BY EXTRACT(YEAR FROM m.release_date) ASC;

/*------------------------ User Ratings ------------------------*/

/* 6. Average rating per user (scatter plot) */
SELECT r.user_id as user,AVG(r.rating) as avgRating
FROM ratings r
GROUP BY r.user_id
ORDER BY r.user_id ASC;

/* 7. Number of ratings per user (scatter plot) */
SELECT r.user_id as user,COUNT(r.rating) as ratings
FROM ratings r
GROUP BY r.user_id
ORDER BY r.user_id ASC;

/* 8. Scatter plot with one point for each user, 
where the x-axis shows the number of ratings for the user and the y-axis shows their average rating. */
SELECT r.user_id as user, COUNT(r.rating) as ratings, AVG(r.rating) as avgRating
FROM ratings r
GROUP BY r.user_id
ORDER BY r.user_id ASC;

/* 9. Average rating by movie genre */
SELECT g.name as genre,AVG(r.rating) as avgRating
FROM genre g
INNER JOIN movie_genres mg
ON mg.genre_id=g.id
INNER JOIN ratings r
ON r.movie_id=mg.movie_id
GROUP BY g.name;