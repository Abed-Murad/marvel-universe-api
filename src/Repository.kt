package com.am

import com.google.gson.Gson
import com.am.model.Hero
import com.am.model.Movie
import java.sql.*
import java.util.*
import kotlin.collections.ArrayList

private var conn: Connection? = null

fun getAllHeroes(): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    val heroes: String
    val query = "SELECT * FROM heroes"

    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)
        ) {
            resultset = stmt.resultSet
        }
        val c = ArrayList<Hero>()
        while (resultset!!.next()) {
            c.add(
                Hero(
                    id = resultset.getInt("id"),
                    name = resultset.getString("name"),
                    description = resultset.getString("description"),
                    thumbnail = resultset.getString("thumbnail"),
                    poster = resultset.getString("poster")
                )
            )
        }
        heroes = Gson().toJson(c)
        return heroes
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } finally {
        releaseResources(resultset, stmt)
    }
    return ""
}

fun getAllMovies(): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    val movies: String
    val query = "SELECT * FROM movies"

    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)
        ) {
            resultset = stmt.resultSet
        }
        val c = ArrayList<Movie>()
        while (resultset!!.next()) {
            c.add(
                Movie(
                    id = resultset.getInt("id"),
                    name = resultset.getString("name"),
                    plot = resultset.getString("plot"),
                    url = resultset.getString("url"),
                    poster = resultset.getString("poster"),
                    releaseDate = resultset.getString("releaseDate")

                )
            )
        }
        movies = Gson().toJson(c)
        return movies
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } finally {
        releaseResources(resultset, stmt)
    }
    return ""


}

fun getHeroById(id: Int): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    val heroes: String
    val query = "SELECT * FROM heroes WHERE id=$id"

    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)) {
            resultset = stmt.resultSet
        }
        lateinit var hero: Hero
        while (resultset!!.next()) {
            hero = Hero(
                id = resultset.getInt("id"),
                name = resultset.getString("name"),
                description = resultset.getString("description"),
                poster = resultset.getString("poster"),
                thumbnail = resultset.getString("thumbnail")
            )
        }
        hero.moviesList = getHeroMovies(id)
        heroes = Gson().toJson(hero)
        return heroes
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } finally {
        releaseResources(resultset, stmt)
    }
    return ""
}

fun getHeroMovies(heroId: Int): ArrayList<Movie> {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    val query =
        "SELECT movies.* FROM movies INNER JOIN heromovies ON movies.id=heromovies.movies_id INNER JOIN heroes ON heromovies.heroes_id=heroes.id WHERE heroes.id = $heroId"
    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)
        ) {
            resultset = stmt.resultSet
        }
        val c = ArrayList<Movie>()
        while (resultset!!.next()) {
            c.add(
                Movie(
                    id = resultset.getInt("id"),
                    name = resultset.getString("name"),
                    plot = resultset.getString("plot"),
                    url = resultset.getString("url"),
                    poster = resultset.getString("poster"),
                    releaseDate = resultset.getString("releaseDate")

                )
            )
        }
        return c
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } finally {
        releaseResources(resultset, stmt)
    }
    return arrayListOf()
}

fun getMovieHeroes(movieId: Int): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroes: String = ""
    val query =
        "SELECT heroes.* FROM movies INNER JOIN heromovies ON movies.id=heromovies.movies_id INNER JOIN heroes ON heromovies.heroes_id=heroes.id WHERE movies.id = $movieId"
    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)) {
            resultset = stmt.resultSet
        }
        val c = ArrayList<Hero>()
        while (resultset!!.next()) {
            c.add(
                Hero(
                    id = resultset.getInt("id"),
                    name = resultset.getString("name"),
                    poster = resultset.getString("poster"),
                    thumbnail = resultset.getString("thumbnail"),
                    description = resultset.getString("description")
                )
            )
        }
        heroes = Gson().toJson(c)
        return heroes
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } finally {
        releaseResources(resultset, stmt)
    }
    return ""
}

private fun releaseResources(resultSet: ResultSet?, stmt: Statement?) {
    if (resultSet != null) {
        try {
            resultSet.close()
        } catch (sqlEx: SQLException) {
            sqlEx.printStackTrace()
        }
    }
    if (stmt != null) {
        try {
            stmt.close()
        } catch (sqlEx: SQLException) {
            sqlEx.printStackTrace()
        }
    }
    if (conn != null) {
        try {
            conn!!.close()
        } catch (sqlEx: SQLException) {
            sqlEx.printStackTrace()
        }
        conn = null
    }
}

/**
 * This method makes a connection to MySQL Server
 * In this example, MySQL Server is running in the local host (so 127.0.0.1)
 * at the standard port 3306
 */
private fun getConnection() {
    val connectionProps = Properties()
    connectionProps["user"] = "root"
    connectionProps["password"] = "root"
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
        conn = DriverManager.getConnection(
            "jdbc:" + "mysql" + "://" + "34.65.97.122" + ":" + "3306" + "/marvel_universe_db?" + "useUnicode=true&serverTimezone=UTC",
            connectionProps
        )
    } catch (ex: SQLException) {
        ex.printStackTrace()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}
