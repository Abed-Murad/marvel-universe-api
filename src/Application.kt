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


@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    //Cross-Origin Resource Sharing
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

    //The ContentNegotiation feature allows you to register and configure custom converters.
    install(ContentNegotiation) {
        gson {
            register(ContentType.Application.Json, GsonConverter(GsonBuilder().create()))
        }
    }

    //embeddedServer is a simple way to start a Ktor application
    embeddedServer(Netty, 8080) {
        routing {
            route("v1/public") {

                get("/heroes") {
                    call.respondText(getAllHeroes(), ContentType.Application.Json)
                }
                get("/heroes/{id}") {
                    val heroId = call.parameters["id"]!!.toInt()
                    call.respondText(getHeroById(heroId), ContentType.Application.Json)
                }
                get("/heroes/{id}/movies") {
                    val heroId = call.parameters["id"]!!.toInt()
                    call.respondText(Gson().toJson(getHeroMovies(heroId)), ContentType.Application.Json)
                }

                get("/movies") {
                    call.respondText(getAllMovies(), ContentType.Application.Json)
                }

                get("/movies/{id}/heroes") {
                    val movieId = call.parameters["id"]!!.toInt()
                    call.respondText(getMovieHeroes(movieId), ContentType.Application.Json)
                }

            }
        }
    }.start(wait = true)
}



fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)