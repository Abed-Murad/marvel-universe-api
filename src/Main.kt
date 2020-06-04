package marvel_universe_api


import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import marvel_universe_api.model.Hero
import marvel_universe_api.model.Heroes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.DateFormat
import java.util.*


//@Suppress("unused")
//@kotlin.jvm.JvmOverloads
//fun Application.module(testing: Boolean = false) {
//
//    install(ContentNegotiation) {
//        gson {
//            setDateFormat(DateFormat.LONG)
//            setPrettyPrinting()
//        }
//    }
//
//    initDB()
//    embeddedServer(Netty, 8080) {
//
//        routing {
//            get("/") {
//                call.respond(getAllHeroes())
//            }
//        }
//    }.start(wait = true)
//
//
//}

fun initDB() {
    val url = "jdbc:mysql://root:root@localhost:3306/marvel_universe_db?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
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
        json = Gson().toJson(c);
    }
    return json
}


//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

