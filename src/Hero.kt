package marvel_universe_api

import org.jetbrains.exposed.sql.Table


object Heroes : Table("heroes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 45)
    val description = varchar("description", 250)
    val poster = varchar("poster", 250)
    override val primaryKey = PrimaryKey(id)
}


object Movies : Table("movies") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 250)
    val poster = varchar("poster", 250)
    val releaseDate = varchar("releaseDate", 250)
    override val primaryKey = PrimaryKey(id)
}

object HeroMovies : Table("heromovies") {
    val heroes_id = integer("id")
    val movies_id = integer("id")
}

data class Hero(val id: Int, val name: String, val description: String, val poster: String)
data class Movie(val id: Int, val name: String, val poster: String, val releaseDate: String)
data class HeroMovie(val name: String,val poster: String)
