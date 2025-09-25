DELETE FROM movie_cast
WHERE ctid IN(
    SELECT t1.ctid
    FROM movie_cast t1
    NATURAL JOIN movie_cast t2
    WHERE t1.ctid>t2.ctid);

UPDATE movie_cast
SET character='UNKNOWN'
WHERE character = null;

DELETE FROM movie_collection
WHERE ctid IN(
    SELECT t1.ctid
    FROM movie_collection t1
    NATURAL JOIN movie_collection t2
    WHERE t1.ctid>t2.ctid);

DELETE FROM movie_crew
WHERE ctid IN(
    SELECT t1.ctid
    FROM movie_crew t1
    NATURAL JOIN movie_crew t2
    WHERE t1.ctid>t2.ctid);

DELETE FROM movie_genres
WHERE ctid IN(
    SELECT t1.ctid
    FROM movie_genres t1
    NATURAL JOIN movie_genres t2
    WHERE t1.ctid>t2.ctid);

DELETE FROM movie_productioncompanies
WHERE ctid IN(
    SELECT t1.ctid
    FROM movie_productioncompanies t1
    NATURAL JOIN movie_productioncompanies t2
    WHERE t1.ctid>t2.ctid);

DELETE FROM movieid_keywordsid /*the movie_keywords referenced in the assignment instructions*/
WHERE ctid IN(
    SELECT t1.ctid
    FROM movieid_keywordsid t1
    NATURAL JOIN movieid_keywordsid t2
    WHERE t1.ctid>t2.ctid);