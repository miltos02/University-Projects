ALTER TABLE movie_cast
add primary key (movie_id,cast_id,person_id,character);

/*4 columns as a primary key so it doesn't duplicate