package marvel_universe_api

import com.google.gson.Gson
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*
import java.util.*

private var conn: Connection? = null

fun getAllHeroes(): String {
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

fun getAllMovies(): String {
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


fun getHeroMovies(heroId: Int): String {
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroMovies = ""
    val query =
        "SELECT movies.name,movies.poster FROM movies INNER JOIN heromovies ON movies.id=heromovies.movies_id INNER JOIN heroes ON heromovies.heroes_id=heroes.id WHERE heroes.id = $heroId"
    try {
        stmt = conn!!.createStatement()
        resultset = stmt!!.executeQuery(query)
        if (stmt.execute(query)
        ) {
            resultset = stmt.resultSet
        }
        val c = ArrayList<HeroMovie>()
        while (resultset!!.next()) {
            c.add(
                HeroMovie(
                    name = resultset.getString("name"),
                    poster = resultset.getString("poster")
                )
            )
        }
        heroMovies = Gson().toJson(c)
        return heroMovies

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
