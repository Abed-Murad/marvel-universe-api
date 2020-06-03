package marvel_universe_api.model

import org.jetbrains.exposed.sql.Table

object Heroes : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val quote = varchar("quote", 255)
    val color = varchar("color", 50)
    val poster = varchar("poster", 255)
    override val primaryKey = PrimaryKey(id)
}


data class Hero(
    val id: Int,
    val name: String,
    val quote: String,
    val color: String,
    val poster: String

)

data class NewHero(
    val id: Int?,
    val name: String,
    val quote: String,
    val color: String,
    val poster: String
)