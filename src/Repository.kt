package marvel_universe_api

import com.google.gson.Gson
import java.sql.*
import java.util.*
import kotlin.collections.ArrayList

private var conn: Connection? = null

fun getAllHeroes(): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroes = ""
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
                    poster = resultset.getString("poster")
                )
            )
        }
        heroes = Gson().toJson(c)
        return heroes

    } catch (ex: SQLException) {    // handle any errors

        ex.printStackTrace()
    } finally {     // release resources

        if (resultset != null) {
            try {
                resultset.close()
            } catch (sqlEx: SQLException) {
            }
            resultset = null
        }
        if (stmt != null) {
            try {
                stmt.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
    return ""
}

fun getHeroById(id: Int): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroes = ""
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
                poster = resultset.getString("poster")
            )
        }
        hero.moviesList = getHeroMovies(id)
        heroes = Gson().toJson(hero)
        return heroes

    } catch (ex: SQLException) {    // handle any errors

        ex.printStackTrace()
    } finally {     // release resources

        if (resultset != null) {
            try {
                resultset.close()
            } catch (sqlEx: SQLException) {
            }
            resultset = null
        }
        if (stmt != null) {
            try {
                stmt.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
    return ""
}

fun getAllMovies(): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var movies = ""
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

    } catch (ex: SQLException) {    // handle any errors

        ex.printStackTrace()
    } finally {     // release resources

        if (resultset != null) {
            try {
                resultset.close()
            } catch (sqlEx: SQLException) {
            }
            resultset = null
        }
        if (stmt != null) {
            try {
                stmt.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
    return ""


}


fun getHeroMovies(heroId: Int): ArrayList<Movie> {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroMovies = ""
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

    } catch (ex: SQLException) {    // handle any errors

        ex.printStackTrace()
    } finally {     // release resources

        if (resultset != null) {
            try {
                resultset.close()
            } catch (sqlEx: SQLException) {
            }
            resultset = null
        }
        if (stmt != null) {
            try {
                stmt.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
    return arrayListOf<Movie>()
}

fun getMovieHeroes(movieId: Int): String {
    getConnection()
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroes:String = ""
    val query =
        "SELECT heroes.* FROM movies INNER JOIN heromovies ON movies.id=heromovies.movies_id INNER JOIN heroes ON heromovies.heroes_id=heroes.id WHERE movies.id = $movieId"
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
                    poster = resultset.getString("poster")
                )
            )
        }
        heroes = Gson().toJson(c)
        return heroes

    } catch (ex: SQLException) {    // handle any errors

        ex.printStackTrace()
    } finally {     // release resources

        if (resultset != null) {
            try {
                resultset.close()
            } catch (sqlEx: SQLException) {
            }
            resultset = null
        }
        if (stmt != null) {
            try {
                stmt.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
    return ""
}

/**
 * This method makes a connection to MySQL Server
 * In this example, MySQL Server is running in the local host (so 127.0.0.1)
 * at the standard port 3306
 */
fun getConnection() {
    val connectionProps = Properties()
    connectionProps["user"] = "root"
    connectionProps["password"] = "root"
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
        conn = DriverManager.getConnection(
            "jdbc:" + "mysql" + "://" + "127.0.0.1" + ":" + "3306" + "/marvel_universe_db?" + "useUnicode=true&serverTimezone=UTC",
            connectionProps
        )
    } catch (ex: SQLException) {
        // handle any errors
        ex.printStackTrace()
    } catch (ex: Exception) {
        // handle any errors
        ex.printStackTrace()
    }
}
