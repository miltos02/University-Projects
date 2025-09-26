/* 3rd step of the excercise: For each person_id in the movie_cast table, 
   check if more than one distinct gender or name value is associated with that ID.
*/
SELECT mc.person_id
FROM movie_cast mc
GROUP BY mc.person_id
HAVING COUNT(DISTINCT mc.gender)>1 OR COUNT(DISTINCT mc.name)>1;

/*4th step of the excercise: For each person_id found in the movie_crew table,
 check if more than one distinct gender or name value corresponds to that ID.
*/
SELECT mc.person_id
FROM movie_crew mc
GROUP BY mc.person_id
HAVING COUNT(DISTINCT mc.gender)>1 OR COUNT(DISTINCT mc.name)>1;