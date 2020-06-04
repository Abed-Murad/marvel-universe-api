-- SELECT * FROM heroes; 
-- SELECT * FROM movies; 
-- SELECT * FROM heromovies; 

-- SELECT name , description FROM heroes;
-- SELECT DISTINCT  name , description FROM heroes;  --The SELECT DISTINCT statement is used to return only distinct (different) values.
-- SELECT COUNT(DISTINCT name) FROM heroes; --The following SQL statement lists the number of different (distinct) customer countries:

-- SELECT heroes.id, heroes.name , heroes.description, heroes.poster FROM heroes 
-- INNER JOIN heromovies ON heroes.id=heromovies.heroes_id;

SELECT movies.name , movies.poster
FROM movies 
INNER JOIN heromovies ON movies.id=heromovies.movies_id
INNER JOIN heroes ON heromovies.heroes_id=heroes.id
where heroes.id = 1