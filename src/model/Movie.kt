package com.am.model

import org.jetbrains.exposed.sql.Table

object Movies : Table("movies") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 250)
    val plot = varchar("plot", 500)
    val url = varchar("url", 500)
    val poster = varchar("poster", 250)
    val releaseDate = varchar("releaseDate", 250)
    override val primaryKey = PrimaryKey(id)
}
data class Movie(
    val id: Int,
    val name: String,
    val plot: String,
    val url: String,
    val poster: String,
    val releaseDate: String
)
