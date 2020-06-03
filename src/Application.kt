package marvel_universe_api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import marvel_universe_api.service.DatabaseFactory
import marvel_universe_api.service.HeroService
import marvel_universe_api.web.hero
import java.io.File
import java.sql.*
import java.util.*


@Suppress("unused") // Referenced in application.conf
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

    install(Authentication) {

    }

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    DatabaseFactory.init()

    val heroService = HeroService()


    install(Routing) {
        hero(heroService)
    }



}





fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
