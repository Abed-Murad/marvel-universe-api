package marvel_universe_api

import com.google.gson.Gson
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*
import java.util.*

private var conn: Connection? = null

fun getAllHeroes(): String {
    var json = ""
    transaction {
        val res = Heroes.selectAll()
        val c = ArrayList<Hero>()
        for (f in res) {
            c.add(
                Hero(
                    id = f[Heroes.id],
                    name = f[Heroes.name],
                    description = f[Heroes.description],
                    poster = f[Heroes.poster]
                )
            )
        }
        json = Gson().toJson(c)
    }
    return json
}

fun getAllMovies(): String {
    var movies = ""
    transaction {
        val res = Movies.selectAll()
        val c = ArrayList<Movie>()
        for (f in res) {
            c.add(
                Movie(
                    id = f[Movies.id],
                    name = f[Movies.name],
                    releaseDate = f[Movies.releaseDate],
                    poster = f[Movies.poster]
                )
            )
        }
        movies = Gson().toJson(c)
    }
    return movies
}


fun executeMySQLQuery(): String {
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var heroMovies = ""
    val query = "SELECT movies.name , movies.poster\n" +
            "FROM movies \n" +
            "INNER JOIN heromovies ON movies.id=heromovies.movies_id\n" +
            "INNER JOIN heroes ON heromovies.heroes_id=heroes.id\n" +
            "where heroes.id = 1"
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
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        conn = DriverManager.getConnection(
            "jdbc:" + "mysql" + "://" + "127.0.0.1" + ":" + "3306" + "/marvel_universe_db?" + "useUnicode=true&serverTimezone=UTC&",
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
