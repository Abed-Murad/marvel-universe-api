package marvel_universe_api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import marvel_universe_api.HeroMovies.heroes_id
import marvel_universe_api.HeroMovies.movies_id
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.ArrayList


@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        gson {
            register(ContentType.Application.Json, GsonConverter(GsonBuilder().create()))
        }
    }

    initDBConnection()

    embeddedServer(Netty, 8080) {
        routing {
            route("v1/public") {
                get("/heroes") {
                    call.respondText(getAllHeroes(), ContentType.Application.Json)
                }

                get("/movies") {
                    call.respondText(getAllMovies(), ContentType.Application.Json)
                }

                get("/heroes-movies") {
                    call.respondText(getAllHeroesMovies(), ContentType.Application.Json)
                }

            }
        }
    }.start(wait = true)

}

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
                    date = f[Movies.date],
                    poster = f[Heroes.poster]
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
                    heroes_id = f[HeroMovies.heroes_id],
                    movies_id = f[HeroMovies.movies_id]
                )
            )
        }
        movies = Gson().toJson(c)
    }
    return movies
}


/**
 * This method makes a connection to MySQL Server
 * In this example, MySQL Server is running in the local host (so 127.0.0.1)
 * at the standard port 3306
 */
fun initDBConnection() {
    val url = "jdbc:mysql://root:root@localhost:3306/marvel_universe_db?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)