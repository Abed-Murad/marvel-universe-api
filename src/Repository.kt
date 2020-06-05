package marvel_universe_api

import com.google.gson.Gson
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.ArrayList


fun getAllHeroes(): String {
    var json: String = ""
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
    var movies: String = ""
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

fun getAllHeroesMovies(): String {
    var movies: String = ""
    transaction {
        val res = HeroMovies.selectAll()
        val c = ArrayList<HeroMovie>()
        for (f in res) {
            c.add(
                HeroMovie(
                    name = f[Movies.name],
                    poster = f[Movies.poster]
                )
            )
        }
        movies = Gson().toJson(c)
    }
    return movies
}

