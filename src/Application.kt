package marvel_universe_api

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
import org.jetbrains.exposed.sql.Database


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

//    initDBConnection()

    embeddedServer(Netty, 8080) {
        routing {
            route("v1/public") {

                get("/heroes") {
                    call.respondText(getAllHeroes(), ContentType.Application.Json)
                }

                get("/movies") {
                    call.respondText(getAllMovies(), ContentType.Application.Json)
                }

                get("/hero-movies") {
                    getConnection()
                    call.respondText(executeMySQLQuery(), ContentType.Application.Json)
                }

            }
        }
    }.start(wait = true)

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