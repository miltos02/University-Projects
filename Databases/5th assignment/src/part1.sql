/*------------------------------ PART A STEP 4 ------------------------------*/

/* Create Person table with primary key person_id */
create table Person(
    gender int,
    person_id int,
    name varchar(40),
	primary key(person_id)
);

/* Create Actor table with primary key person_id and foreign key corresponding to the Person table */
create table Actor(
    gender int,
    person_id int,
    name varchar(40),
	primary key(person_id),
    foreign key(person_id) references Person(person_id)
);

/* Create CrewMember table with primary key person_id and foreign key corresponding to the Person table */
create table CrewMember(
    gender int,
    person_id int,
    name varchar(40),
	primary key(person_id),
    foreign key(person_id) REFERENCES Person(person_id)
);

/*------------------------------ PART A STEP 5 ------------------------------*/

/* In assignment 4, before sending the email on May 13 regarding how to fix the errors in the initial dataset, 
we had already found them because we had started the assignment early and took the initiative to delete them 
with our own set of commands rather than updating and keeping them. Due to this, in this query when we run the 
following commands, no violations of the functional dependencies are found, and therefore, there is no need 
to perform any update. Along with the files for this assignment, we will send you the respective screenshot 
that we had taken proactively to avoid any confusion. (In a related email we sent you on May 19, you told us 
that there would be no issue with the grading, we will also attach a screenshot of your response for reassurance) */
SELECT mc.person_id as movieCast_person,mc.gender,mc.name,mcr.person_id as movieCrew_person,mcr.gender,mcr.name
FROM movie_cast mc
FULL OUTER JOIN movie_crew mcr
ON mc.person_id=mcr.person_id
WHERE mc.gender<>mcr.gender OR mc.name<>mcr.name
GROUP BY mc.person_id,mc.gender,mc.name,mcr.person_id,mcr.gender,mcr.name;

/*------------------------------ PART A STEP 6 ------------------------------*/

/* Insert the corresponding records person_id, name, and gender into the Person, Actor, and CrewMember tables without 
   including duplicates. */
INSERT INTO person(person_id,gender,name) 
SELECT DISTINCT person_id,gender,name
FROM movie_cast
UNION
SELECT DISTINCT person_id,gender,name
FROM movie_crew;

INSERT INTO actor(person_id,gender,name) 
SELECT DISTINCT person_id,gender,name
FROM movie_cast;

INSERT INTO crewmember(person_id,gender,name) 
SELECT DISTINCT person_id,gender,name
FROM movie_crew;

/*------------------------------ PART A STEP 7 ------------------------------*/

/* Create table for backup purposes */
create table Movie_Crew2(
   movie_id int,
   department varchar(20),
   gender int,
   person_id int,
   job varchar(60),
   name varchar(40),
   primary key(movie_id,department,job,person_id)
);

/* Create table for backup purposes */
create table Movie_Cast2(
   movie_id int,
   cast_id int,
   character varchar(390),
   gender int,
   person_id int,
   name varchar(40),
   primary key(movie_id,cast_id,person_id,character)
);

/* Copy the movie_cast and movie_crew tables to the respective tables we created to have a backup. */
INSERT INTO movie_crew2(movie_id,department,gender,person_id,job,name) 
SELECT *
FROM movie_crew;

INSERT INTO movie_cast2(movie_id,cast_id,character,gender,person_id,name)
SELECT *
FROM movie_cast;*/

/* Update the movie_cast and movie_crew tables so that 
   the tables are normalized and the database is in BCNF form. */
ALTER TABLE movie_cast
DROP COLUMN gender,
DROP COLUMN name,
add foreign key(person_id) references person(person_id);

ALTER TABLE movie_crew
DROP COLUMN gender,
DROP COLUMN name,
add foreign key(person_id) references person(person_id);