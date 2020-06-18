-- SELECT * FROM heroes; 
-- SELECT * FROM movies; 
-- SELECT * FROM heromovies; 

-- SELECT name , description FROM heroes;
-- SELECT DISTINCT  name , description FROM heroes;  --The SELECT DISTINCT statement is used to return only distinct (different) values.
-- SELECT COUNT(DISTINCT name) FROM heroes; --The following SQL statement lists the number of different (distinct) customer countries:

-- SELECT heroes.id, heroes.name , heroes.description, heroes.poster FROM heroes 
-- INNER JOIN heromovies ON heroes.id=heromovies.heroes_id;

-- SELECT heroes.*
-- FROM movies 
-- INNER JOIN heromovies ON movies.id=heromovies.movies_id
-- INNER JOIN heroes ON heromovies.heroes_id=heroes.id
-- where movies.id = 1

-- SELECT heroes.* FROM movies INNER JOIN heromovies ON movies.id=heromovies.movies_id
-- INNER JOIN heroes ON heromovies.heroes_id=heroes.id WHERE movies.id = 2
