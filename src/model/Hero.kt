package marvel_universe_api.model

import org.jetbrains.exposed.sql.Table

object Heroes : Table() {
    val id = integer("id")
    val name = varchar("name", 255)
    val quote = varchar("quote", 255)
    val color = varchar("color", 255)
    val poster = varchar("poster", 255)
    val dateUpdated = long("dateUpdated")
    override val primaryKey = PrimaryKey(id)
}


data class Hero(
    val id: Int,
    val name: String,
    val quote: String,
    val color: String,
    val poster: String,
    val dateUpdated: Long

)

data class NewHero(
    val id: Int?,
    val name: String,
    val quote: Int,
    val color: String,
    val poster: String
)