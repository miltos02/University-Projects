ALTER TABLE ratings ALTER COLUMN rating
TYPE float USING (rating::float);
ALTER TABLE movie ALTER COLUMN runtime
TYPE float USING (runtime::float);

/* Find the titles of movies with a runtime longer than 100 minutes, which have ratings between 2 and 5 
Output: 930 rows */
SELECT m.title as movieTitle, avg(r.rating) as avgRating
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id
WHERE m.runtime>100
GROUP BY m.id, m.title 
HAVING avg(r.rating) BETWEEN 2.0 AND 5.0;

/* Find the titles of movies in which Bruce Willis acted, along with the name of the character he portrayed 
Output: 47 rows */
SELECT m.title as movieTitle,mc.character
FROM movie m
INNER JOIN movie_cast mc
ON m.id=mc.movie_id
WHERE mc.person_id=62;

/* Find the range of ratings that the movie "TITANIC" received
Output: 9 rows */
SELECT DISTINCT m.title as movieTitle, r.rating
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id
WHERE movie_id=597;

/* Find the titles of the 10 most expensive movies based on their budget
Output: 10 rows */
SELECT m.title as movieTitle,m.budget
FROM movie m
ORDER BY m.budget DESC
limit 10;

/* Find the titles of movies starting with T, sorted by their rating from lowest to highest
Output: 7483 rows */
SELECT m.title as movieTitle,r.rating
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id
WHERE m.title LIKE 'T%'
ORDER BY r.rating;

/* Find the title(s) of the movie(s) with the largest budget, along with the budget
Output: 3 rows */
SELECT m.title, m.budget as mostExpensiveBudget
FROM movie m
WHERE m.budget=(SELECT MAX(m.budget) FROM movie m);

/* Find the movies with the worst ratings (among those that have been rated), along with their popularity
Output: 237 rows */
SELECT m.title as movieTitle,m.popularity 
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id 
WHERE r.rating=(SELECT MIN(r.rating) FROM ratings r);

/* Find the titles of movies with the theme of a midlife crisis, along with their revenue
Output: 13 rows */
SELECT m.title as movieTitle,m.budget
FROM keywords_set ks
INNER JOIN movieid_keywordsid mk
ON ks.id=mk.keyword_id
INNER JOIN movie m
ON mk.movie_id=m.id
WHERE ks.id=1599;

/* Find the titles of the 10 most successful movies featuring female leads, along with their revenue
Output: 10 rows */
SELECT DISTINCT m.title as movieTitle,m.budget
FROM movie m
INNER JOIN movie_cast mc
ON m.id=mc.movie_id
WHERE mc.gender=1
ORDER BY m.budget DESC
limit 10;

/* For 1000 movies with a budget of 0, find which of them have ratings and which do not, along with their ratings
Output: 1000 rows */
SELECT m.title as movieTitle,avg(r.rating) as avgRating
FROM movie m
LEFT OUTER JOIN ratings r
ON m.id=r.movie_id
WHERE m.budget=0
GROUP BY m.id,m.title
limit 1000;

/* Find the titles of movies that are not in English with a rating above 3, along with their language
Output: 191 rows */
SELECT m.title as movieTitle,m.original_language as language
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id
WHERE m.original_language<>'en'
GROUP BY m.title,m.original_language
HAVING avg(r.rating)>3;

/* Find the ratings of the 100 cheapest movies, along with their website if available
Output: 100 rows */
SELECT avg(r.rating) as avgRating, m.homepage 
FROM movie m
LEFT OUTER JOIN ratings r
ON m.id=r.movie_id
GROUP BY m.homepage,m.budget
HAVING avg(r.rating) BETWEEN 0.0 AND 5.0
ORDER BY m.budget
limit 100;