package marvel_universe_api.web

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import marvel_universe_api.model.NewHero
import marvel_universe_api.service.HeroService
import java.io.File

fun Route.hero(heroService: HeroService) {

    route("v1/public") {

        get("/heroes") {
            call.respond(heroService.getAllWidgets())
        }

        post("/heroes") {
            val widget = call.receive<NewHero>()
            call.respond(HttpStatusCode.Created, heroService.addHero(widget))
        }

    }

}
