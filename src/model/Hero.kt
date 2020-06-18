package com.am.model

import org.jetbrains.exposed.sql.Table

object Heroes : Table("heroes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 45)
    val description = varchar("description", 250)
    val poster = varchar("poster", 250)
    val thumbnail = varchar("thumbnail", 500)
    override val primaryKey = PrimaryKey(id)
}

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val poster: String,
    val thumbnail: String,
    var moviesList: List<Movie>? = null
)