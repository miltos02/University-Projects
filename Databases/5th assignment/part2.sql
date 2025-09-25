/* Find the titles of the movies in which Mark Hamill played 
with an average rating between 2 and 5, along with their average ratings */
SELECT m.title as movieTitle,avg(r.rating) as avgRating
FROM movie m
INNER JOIN ratings r
ON m.id=r.movie_id
WHERE m.title IN
(
		SELECT m.title
		FROM movie m
		INNER JOIN movie_cast mc
		ON m.id=mc.movie_id
		WHERE mc.person_id IN
		(
			SELECT p.person_id
			FROM person p
			WHERE p.person_id=2
		)
)
GROUP BY m.id, m.title 
HAVING avg(r.rating) BETWEEN 2.0 AND 5.0;

/* Find all the movies in which Uma Thurman plays, along with their budget */
SELECT m.title as movieTitle,m.budget as budget
FROM movie m
INNER JOIN movie_cast mc
ON m.id=mc.movie_id
WHERE mc.person_id IN
(
	SELECT p.person_id
	FROM person p
	WHERE p.name='Uma Thurman'
)
GROUP BY m.title,m.budget;

/* Find all directors who have directed movies with an average rating of exactly 5 */
SELECT p.name as Director
FROM person p
INNER JOIN movie_crew mc 
ON mc.person_id=p.person_id
WHERE mc.job='Director' AND mc.movie_id IN
(
		SELECT m.id
		FROM movie m
		INNER JOIN ratings r
		ON m.id=r.movie_id
		GROUP BY m.id
		HAVING avg(r.rating)=5.0
);