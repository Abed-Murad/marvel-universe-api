package marvel_universe_api

import org.jetbrains.exposed.sql.Table
import java.sql.Date
import java.time.Instant.now


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
    val date = varchar("date", 250)
    override val primaryKey = PrimaryKey(id)
}

object HeroMovies : Table("heromovies") {
    val heroes_id = integer("id").autoIncrement()
    val movies_id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey( heroes_id , movies_id )
}

data class Hero(val id: Int, val name: String, val description: String, val poster: String)
data class Movie(val id: Int, val name: String, val poster: String, val date: String)
data class HeroMovie(val heroes_id: Int,val movies_id: Int)
