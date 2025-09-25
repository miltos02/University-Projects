alter table movie_cast
add foreign key (movie_id) references movie(id);

alter table movie_collection
add foreign key (movie_id) references movie(id),
add foreign key (collection_id) references collection(id)

alter table movie_productioncompanies
add foreign key(movie_id) references movie(id),
add foreign key(pc_id) references productioncompany(id);

alter table movie_crew
add foreign key(movie_id) references movie(id);

alter table movie_genres
add foreign key(movie_id) references movie(id),
add foreign key (genre_id) references genre (id);

alter table ratings 
add foreign key(movie_id) references movie(id);

alter table movieid_keywordsid
add foreign key(movie_id) references movie(id),
add foreign key(keyword_id) references keywords_set(id);